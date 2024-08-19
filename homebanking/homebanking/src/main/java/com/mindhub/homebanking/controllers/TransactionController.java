package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping("/api")

public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                     @RequestParam int amount,@RequestParam String description,
                                                     @RequestParam String toAccountNumber,@RequestParam String fromAccountNumber) {

        String username = authentication.getName();
        Optional<Client> optionalClient = clientRepository.findByEmail(username);
        Optional<Account> optionalAccountDestiny = accountRepository.findByNumber(toAccountNumber);
        Optional<Account> optionalAccountOrigin = accountRepository.findByNumber(fromAccountNumber);

        if ((amount == 0) || (description == null) || (toAccountNumber == null) || (fromAccountNumber == null)){
            return new ResponseEntity<>("Los Parametros De Entrada Están Vacios", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("La Cuenta De Origen Y La De Destino Son La Misma", HttpStatus.FORBIDDEN);
        }else{
            if (optionalClient.isPresent() && optionalAccountDestiny.isPresent() && optionalAccountOrigin.isPresent()) {
                Client client = optionalClient.get();
                Account accountDestiny = optionalAccountDestiny.get();
                Account accountOrigin = optionalAccountOrigin.get();
                //Verificar que la cuenta de origen corresponda al cliente autenticado
                if (accountOrigin.getClient()==client && accountOrigin.getBalance()>= amount){

                    Transaction transactionsOrigin= new Transaction(Transaction.TransactionType.Debito,accountOrigin,LocalDateTime.now(),amount,description);
                    Transaction transactionsDestiny= new Transaction(Transaction.TransactionType.Credito,accountDestiny,LocalDateTime.now(),amount,description);
                    transactionRepository.save(transactionsOrigin);
                    transactionRepository.save(transactionsDestiny);

                    //descuento de las cuentas
                    Double auxOrigin=accountOrigin.getBalance()-amount;
                    Double auxDestiny=accountDestiny.getBalance()+amount;

                    //actualizo los montos

                    accountOrigin.setBalance(auxOrigin);
                    accountDestiny.setBalance(auxDestiny);

                    return new ResponseEntity<>("201 tranferencia con exito",HttpStatus.CREATED);


                }else{  //Cuenta origen o saldo
                    return new ResponseEntity<>("Problema De Cuenta", HttpStatus.FORBIDDEN);
                }
                // Se deben crear dos transacciones, una con el tipo de transacción “DEBIT”
                // asociada a la cuenta de origen y la otra con el tipo de transacción “CREDIT” asociada a la cuenta de destino.
                // A la cuenta de origen se le restará el monto indicado en la petición y a la cuenta de destino se le sumará el mismo monto.

            }else{
                //en este else se encuentra el control de existencia de cuenta de origen y existencia de cliente y existencia de cuenta de destino
                return ResponseEntity.notFound().build();
            }
        }


    }
}
