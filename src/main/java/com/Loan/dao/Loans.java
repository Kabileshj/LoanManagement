package com.Loan.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Loans")
@Data

public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loanRef", nullable = false)
    private long loanRef;

    @Column(name = "loanBal", nullable = false)
    private Double loanBal;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Column(name = "loanTenure", nullable = false)
    private Integer loanTenure;

    @Column(name = "createdOn")
    private Date createdOn;

    @Column(name = "closedOn")
    private Date closedOn;
}