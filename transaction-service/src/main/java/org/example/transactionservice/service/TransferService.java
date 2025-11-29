package org.example.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.AppFeignClient.AccountClient;
import org.example.transactionservice.DTO.TransferRequest;
import org.example.transactionservice.model.Account;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountClient accountClient;

    public String transfer(TransferRequest request) {
        // 1. Récupérer les comptes
        Account fromAccount = accountClient.getAccount(request.getFromAccountId());
        Account toAccount = accountClient.getAccount(request.getToAccountId());

        // 2. Vérifier le solde
        if (fromAccount.getBalance() < request.getAmount()) {
            return "Solde insuffisant";
        }

        // 3. Débiter le compte source
        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        accountClient.updateAccount(fromAccount.getId(), fromAccount);

        // 4. Créditer le compte destination
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());
        accountClient.updateAccount(toAccount.getId(), toAccount);

        return "Transfert réussi : " + request.getAmount() + " " + request.getCurrency();
    }
}