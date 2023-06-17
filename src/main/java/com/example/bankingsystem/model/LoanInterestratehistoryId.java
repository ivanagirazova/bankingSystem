package com.example.bankingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class LoanInterestratehistoryId implements Serializable {
    private static final long serialVersionUID = -2442957292576752510L;
    @Column(name = "loanid", nullable = false)
    private Integer loanid;
    @Column(name = "interestratehistoryid", nullable = false)
    private Integer interestratehistoryid;
}