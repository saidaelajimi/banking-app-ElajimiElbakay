package org.example.transactionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)  // ← AJOUTE CETTE LIGNE Ignorer les propriétés inconnues
public class Account {
    private Long id;
    private String owner;
    private Double balance;
    private String currency;
}