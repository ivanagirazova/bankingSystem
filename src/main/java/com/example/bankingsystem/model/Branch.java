package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "branch")
@Entity
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "branchemail", nullable = false, length = 50)
    private String branchemail;

    @Column(name = "branchphonenumber", nullable = false, length = 15)
    private String branchphonenumber;
}