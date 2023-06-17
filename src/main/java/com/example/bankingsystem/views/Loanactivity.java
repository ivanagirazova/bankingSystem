package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Loanactivity {

  private long loanid;
  private long transactionid;
  private Date transactiondate;
  private long transactionamount;
  private long principalamount;
  private long interestamount;
  private long amountborrowed;
  private long amountowed;
  private long interestrate;
  private boolean isfixedrate;
  private String interesttype;
  private Date rateeffectivefrom;
  private Date rateeffectiveto;
  private Date ratehistoryfrom;
  private Date ratehistoryto;

}
