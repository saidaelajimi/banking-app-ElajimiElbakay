package org.example.reportingservice.service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {

    private final WebClient webClient;

    public ExchangeRateService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.exchangerate-api.com/v4/latest/")
                .build();
    }

    public Mono<String> getExchangeRate(String from, String to) {
        return webClient.get()
                .uri(from)
                .retrieve()
                .bodyToMono(String.class);
    }
}
