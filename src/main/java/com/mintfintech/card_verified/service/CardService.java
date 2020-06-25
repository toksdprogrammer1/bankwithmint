package com.mintfintech.card_verified.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintfintech.card_verified.dto.CardResponse;
import com.mintfintech.card_verified.dto.RemoteResponse;
import com.mintfintech.card_verified.dto.Response;
import com.mintfintech.card_verified.exception.BadGatewayException;
import com.mintfintech.card_verified.exception.BadRequestException;
import com.mintfintech.card_verified.exception.ResourceNotFoundException;
import com.mintfintech.card_verified.model.Card;
import com.mintfintech.card_verified.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class CardService {

    Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BinListCall binListCall;

    @Value("${binlist-url}")
    private String binListUrl;


    public Response verify(String cardNo) throws BadGatewayException, BadRequestException{
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

}
