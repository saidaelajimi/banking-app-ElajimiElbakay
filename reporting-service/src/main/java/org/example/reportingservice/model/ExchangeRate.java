package org.example.reportingservice.model;

import lombok.Data;

@Data
public class ExchangeRate {
    private String base;
    private String target;
    private Double rate;
}