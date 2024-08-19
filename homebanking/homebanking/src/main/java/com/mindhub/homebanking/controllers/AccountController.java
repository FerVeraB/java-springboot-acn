package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.http.HttpStatus;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }



     @GetMapping("/accounts/{id}")
     public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(account -> {
                    // Aquí conviertes el account a ClientDTO
                    AccountDTO accountDTO = new AccountDTO(account);
                    return accountDTO;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/clients/current/accounts/{accountNumber}")
    public ResponseEntity<String> deleteAccount(Authentication authentication, @PathVariable String accountNumber) {
        String username = authentication.getName();
        Optional<Client> optionalClient = clientRepository.findByEmail(username);

        if (optionalClient.isPresent()){
            Client client = optionalClient.get();
            Optional<Account> accountOptional = accountRepository.findByNumber(accountNumber);
            if (!accountOptional.isPresent()){
                return new ResponseEntity<>("Cuenta no encontrada", HttpStatus.NOT_FOUND);
            }
            Account account = accountOptional.get();
            if (!client.getAccounts().contains(account)){
                return new ResponseEntity<>("La cuenta no pertenece al cliente", HttpStatus.FORBIDDEN);
            }
            transactionRepository.deleteAll(account.getTransactions());
            accountRepository.delete(account);
            return new ResponseEntity<>("Cuenta eliminada", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Error al borrar la cuenta", HttpStatus.NOT_FOUND);
        }
    }

}
