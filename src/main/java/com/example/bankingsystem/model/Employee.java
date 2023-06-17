package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "employee", indexes = {
        @Index(name = "uniqueemployee", columnList = "userid", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "employee_userid_key", columnNames = {"userid"})
})
@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "jobtitle", nullable = false, length = 50)
    private String jobtitle;

    @Column(name = "ismanager", nullable = false)
    private Boolean ismanager = false;

    @ManyToOne
    @JoinColumn(name = "managedby")
    private Employee managedby;

    @ManyToOne(optional = false)
    @JoinColumn(name = "branchid", nullable = false)
    private Branch branchid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User userid;
}