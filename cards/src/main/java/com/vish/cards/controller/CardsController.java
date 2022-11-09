package com.vish.cards.controller;

import com.vish.cards.model.CardInfo;
import com.vish.cards.repository.CardRepository;
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

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/{customerId}/cards")
    public List<CardInfo> getAccountDetails(@PathVariable int customerId){
        return cardRepository.findByCustomerId(customerId);
    }
}
