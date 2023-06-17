package com.example.bankingsystem.views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Branchemployees {

  private long branchid;
  private long employeeid;
  private String jobtitle;
  private boolean ismanager;
  private String firstname;
  private String lastname;
  private String email;
  private String phonenumber;

}
