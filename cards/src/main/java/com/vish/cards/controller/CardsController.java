package com.vish.cards.controller;

import com.vish.cards.model.CardInfo;
import com.vish.cards.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardsController {
    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/{customerId}/cards")
    public List<CardInfo> getAccountDetails(@PathVariable int customerId){
        logger.info("card details fetch started");
        return cardRepository.findByCustomerId(customerId);
    }
}
