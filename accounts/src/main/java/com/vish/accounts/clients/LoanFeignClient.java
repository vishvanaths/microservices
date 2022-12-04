package com.vish.accounts.clients;

import com.vish.accounts.model.Customer;
import com.vish.accounts.model.LoanInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;

@FeignClient("loans")
public interface LoanFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "loansservice/customers/{customerId}/loans", consumes = "application/json")
    List<LoanInfo> getLoadDetails(@PathVariable int customerId);

}
