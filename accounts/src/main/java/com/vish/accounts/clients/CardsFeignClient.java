package com.vish.accounts.clients;

import com.vish.accounts.model.CardInfo;
import com.vish.accounts.model.Customer;
import com.vish.accounts.model.LoanInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("cards")
public interface CardsFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "cardservice/customers/{customerId}/cards", consumes = "application/json")
    List<CardInfo> getCardDetails(@PathVariable int customerId);
}
