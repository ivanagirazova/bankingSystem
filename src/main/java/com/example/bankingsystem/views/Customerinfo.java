package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Customerinfo {

  private long customerid;
  private String firstname;
  private String lastname;
  private Date dateofbirth;
  private String city;
  private String address;
  private String email;
  private String phonenumber;

}
