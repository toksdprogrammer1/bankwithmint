package com.mintfintech.card_verified.controller;

import com.mintfintech.card_verified.dto.Response;
import com.mintfintech.card_verified.exception.BadGatewayException;
import com.mintfintech.card_verified.exception.BadRequestException;
import com.mintfintech.card_verified.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card-scheme")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/verify/{cardNo}")
    public ResponseEntity<Response> verify(@PathVariable String cardNo) throws BadGatewayException, BadRequestException {
        return new ResponseEntity<Response>(cardService.verify(cardNo), HttpStatus.OK);
    }
}
