package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Exchangeratestoday {

  private long exchangerateid;
  private Date date;
  private double exchangerate;
  private String basecurrencycode;
  private String basecurrencyname;
  private String currencycode;
  private String currencyname;

}
