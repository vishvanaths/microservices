package com.vish.accounts.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CustomerDetails {
    private AccountInfo accountInfo;

    private List<CardInfo> cardInfos;

    private List<LoanInfo> loanInfos;
}
