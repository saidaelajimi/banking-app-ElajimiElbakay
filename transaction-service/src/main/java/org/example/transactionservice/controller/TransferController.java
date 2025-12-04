package org.example.transactionservice.controller;
import lombok.RequiredArgsConstructor;
import org.example.transactionservice.AppFeignClient.AccountClient;
import org.example.transactionservice.DTO.TransferRequest;
import org.example.transactionservice.model.Account;
import org.example.transactionservice.service.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final AccountClient accountClient;
    private final TransferService transferService;

    @PostMapping
    public String transfer(@RequestBody TransferRequest request) {
        return transferService.transfer(request);
    }
    @GetMapping("/test/{id}")
    public Account testGetAccount(@PathVariable Long id) {
        return accountClient.getAccount(id);
    }
}