package com.example.bankingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "interestrates")
@Entity
@Getter
@Setter
public class Interestrate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "isfixedrate", nullable = false)
    private Boolean isfixedrate = false;

    @Column(name = "interesttype", nullable = false, length = 50)
    private String interesttype;

    @Column(name = "datefrom", nullable = false)
    private LocalDate datefrom;

    @Column(name = "dateto", nullable = false)
    private LocalDate dateto;
}