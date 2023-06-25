package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Customeraccounts {

  private long customerid;
  private long accountid;
  private String accountnumber;
  private String accounttype;
  private double balance;
  private long cardid;
  private String currencyCode;
  private String cardnumber;
  private String cardtype;
  private Date expiredate;

}
