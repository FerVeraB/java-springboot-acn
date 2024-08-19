package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientLoanRepository clientLoansRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoanApplicationDTO() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<String>newLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        if (loanApplicationDTO.getToAccountNumber().isEmpty() ){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        String username = authentication.getName();
        Long loanTypeId = loanApplicationDTO.getLoanId();
        String toAccountNumber = loanApplicationDTO.getToAccountNumber();


        Optional<Client> optionalClient = clientRepository.findByEmail(username);
        Optional<Loan> optionalLoan = loanRepository.findById(loanTypeId);
        Optional<Account> optionalAccount = accountRepository.findByNumber(toAccountNumber);

        if (optionalClient.isEmpty() || optionalLoan.isEmpty() || toAccountNumber.isEmpty() ){
            return new ResponseEntity<>("No se han encontrado datos", HttpStatus.FORBIDDEN);
        }

        //verifico que los datos no esten vacios, revisar payment que lo tengo en string
        if (loanApplicationDTO.getAmount()== 0 || loanApplicationDTO.getToAccountNumber().isEmpty()  || Objects.equals(loanApplicationDTO.getLoanId(), "")) {
            return new ResponseEntity<>("Datos incorrectos", HttpStatus.FORBIDDEN);
        }

        //verificar que exista una cuenta
        if(optionalAccount.isEmpty()){
            return new ResponseEntity<>("No es posible encontrar la cuenta ",HttpStatus.FORBIDDEN);
        }

        Client client = optionalClient.get();
        Account account = optionalAccount.get();
        Loan loan = optionalLoan.get();


        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("No se han autenticado las cuentas",HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount()>loan.getMaxAmount() || loanApplicationDTO.getAmount() <=0){
            return new ResponseEntity<>("Excede el monto maximo ",HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Error ",HttpStatus.FORBIDDEN);
        }

        int montito = loanApplicationDTO.getAmount();
        int loanInterest= (int) ((montito*0.20)+montito);
        double loanPayments = Math.floor(loanInterest / Double.parseDouble(loanApplicationDTO.getPayments()));


        ClientLoan clientLoans=new ClientLoan(loanApplicationDTO.getAmount(),loanApplicationDTO.getPayments(),client,loan);

        Transaction transaction = new Transaction(Transaction.TransactionType.Credito,account, LocalDateTime.now(), loanApplicationDTO.getAmount(),loan.getName());
        transactionRepository.save(transaction);
        clientLoansRepository.save(clientLoans);

//descuento de las cuentas

        int auxAmount= (int) (loanApplicationDTO.getAmount()+ account.getBalance());
        account.setBalance(auxAmount);


        return new ResponseEntity<>("SE APLICADO EL PRESTAMO",HttpStatus.CREATED);
    }



}
