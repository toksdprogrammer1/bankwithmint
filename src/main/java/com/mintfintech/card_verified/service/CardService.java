package com.mintfintech.card_verified.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintfintech.card_verified.dto.CardResponse;
import com.mintfintech.card_verified.dto.RemoteResponse;
import com.mintfintech.card_verified.dto.Response;
import com.mintfintech.card_verified.dto.StatsResponse;
import com.mintfintech.card_verified.exception.BadGatewayException;
import com.mintfintech.card_verified.exception.BadRequestException;
import com.mintfintech.card_verified.exception.ResourceNotFoundException;
import com.mintfintech.card_verified.model.Card;
import com.mintfintech.card_verified.repository.CardRepository;
import com.mintfintech.card_verified.stream.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class CardService {

    Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaStreams kafkaStreams;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BinListCall binListCall;

    @Value("${binlist-url}")
    private String binListUrl;


    public Response verify(String cardNo) throws BadGatewayException, BadRequestException, JsonProcessingException {
        CardResponse cardResponse = new CardResponse();

        Card card = cardRepository.findByCardNo(cardNo);
        if (card != null){
            card.setHit(card.getHit() + 1);
            cardRepository.save(card);
            BeanUtils.copyProperties(card, cardResponse);
        }
        else{
            Map map = new HashMap();
            RemoteResponse response = binListCall.getCall(binListUrl + cardNo, map);
            if (response.getCode() == HttpStatus.OK.value()){
                logger.info(response.getResponseBody());
                card = mapResponseBodyToCard(response.getResponseBody());
                card.setCardNo(cardNo);
                card.setHit(1L);
                cardRepository.save(card);
                BeanUtils.copyProperties(card, cardResponse);
            }
            else {
                throw new BadRequestException("Invalid card number " + cardNo);
            }
        }
        //publishToKafka(cardResponse);
        return new Response(true, cardResponse);
    }

    private Card mapResponseBodyToCard(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payload = null;
        try {
            payload = objectMapper.readTree(body);
        } catch (IOException | NullPointerException e) {
            logger.error(e.getMessage());
            Card card = new Card();
            card.setHit(1);
            cardRepository.save(card);
        }
        return new Card(payload.get("scheme").asText(),
                payload.get("type").asText(),
                payload.get("bank").get("name").asText(), true);
    }

    public StatsResponse getStats(int start, int limit){
        Pageable paging = PageRequest.of(start, limit);
        Page<Card> cardPage = cardRepository.findAll(paging);
        Map<String, Long> response = new HashMap<>();
        cardPage.getContent().forEach(card -> response.put(card.getCardNo(), card.getHit()));
        return new StatsResponse(true, response, start + 1, limit, cardPage.getTotalElements());
    }

    public void publishToKafka(CardResponse cardResponse) throws JsonProcessingException {

        MessageChannel messageChannel = kafkaStreams.initiate();
        messageChannel.send(MessageBuilder
                .withPayload(new ObjectMapper().writeValueAsString(cardResponse))
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());

    }

    @StreamListener(KafkaStreams.INPUT)
    public void consumer(@Payload String payload, @Headers MessageHeaders headers){
        logger.info("Received payload: {}", payload);
        Acknowledgment acknowledgment = headers.get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            acknowledgment.acknowledge();
            logger.info("Acknowledgment provided");
        }
    }

}
