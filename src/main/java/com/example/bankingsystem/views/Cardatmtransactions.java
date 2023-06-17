package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cardatmtransactions {

  private long transactionid;
  private String type;
  private java.sql.Date date;
  private long amount;
  private long cardid;
  private long accounttransactionid;

}
