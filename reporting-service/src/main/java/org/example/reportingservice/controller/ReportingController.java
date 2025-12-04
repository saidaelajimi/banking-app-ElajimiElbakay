package org.example.reportingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.reportingservice.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class ReportingController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public Mono<String> getRates(@RequestParam String from, @RequestParam String to) {
        return exchangeRateService.getExchangeRate(from, to);
    }
}