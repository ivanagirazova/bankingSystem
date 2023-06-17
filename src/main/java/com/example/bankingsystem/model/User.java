package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "\"User\"")
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "embg", nullable = false, unique = true, length = 13)
    private String embg;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Column(name = "dateofbirth", nullable = false)
    private LocalDate dateofbirth;

    @Column(name = "city", nullable = false, length = 30)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phonenumber", nullable = false, length = 15)
    private String phonenumber;
}