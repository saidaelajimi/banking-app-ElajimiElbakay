package org.example.transactionservice.model;

import lombok.Data;

@Data
public class Account {
    private Long id;
    private String owner;
    private Double balance;
    private String currency;
}