package com.mintfintech.card_verified.controller;

import com.mintfintech.card_verified.dto.Response;
import com.mintfintech.card_verified.dto.StatsResponse;
import com.mintfintech.card_verified.exception.BadGatewayException;
import com.mintfintech.card_verified.exception.BadRequestException;
import com.mintfintech.card_verified.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card-scheme")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/verify/{cardNo}")
    public ResponseEntity<Response> verify(@PathVariable String cardNo) throws BadGatewayException, BadRequestException {
        return new ResponseEntity<Response>(cardService.verify(cardNo), HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(
            @RequestParam(defaultValue = "1") int start,
            @RequestParam(defaultValue = "1") int limit ) {
        if (start > 0) start--; //to ensure the page start from 1
        return new ResponseEntity<>(cardService.getStats(start, limit), HttpStatus.OK);
    }
}
