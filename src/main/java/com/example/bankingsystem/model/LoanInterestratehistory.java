package com.example.bankingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "loan_interestratehistory")
@Entity
@Getter
@Setter
public class LoanInterestratehistory {
    @EmbeddedId
    private LoanInterestratehistoryId id;

    @Column(name = "datefrom", nullable = false)
    private LocalDate datefrom;

    @Column(name = "dateto", nullable = false)
    private LocalDate dateto;

}