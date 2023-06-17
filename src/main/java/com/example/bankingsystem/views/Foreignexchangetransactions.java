package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Foreignexchangetransactions {

  private long transactionid;
  private Timestamp date;
  private double amount;
  private double convertedamount;
  private String fromcurrencycode;
  private String fromcurrencyname;
  private String tocurrencycode;
  private String tocurrencyname;
  private double fromexchangerate;
  private double toexchangerate;
  private String fromaccountnumber;
  private String toaccountnumber;


}
