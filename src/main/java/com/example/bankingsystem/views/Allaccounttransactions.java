package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Allaccounttransactions {

  private long accountid;
  private long transactionid;
  private String type;
  private Date date;
  private long amount;
  private String currencycode;
  private long accountfrom;
  private long accountto;
}
