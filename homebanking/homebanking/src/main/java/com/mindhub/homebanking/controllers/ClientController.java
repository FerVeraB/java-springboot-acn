package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utils.CardUtils.*;
import static java.util.stream.Collectors.toList;



@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(client -> {
                    // Aquí conviertes el cliente a ClientDTO
                    ClientDTO clientDTO = new ClientDTO(client);
                    return clientDTO;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clients/current")
    public ResponseEntity<ClientDTO> getClientLogin(Authentication authentication) {
        try {
            // Obtener el nombre de usuario (email) del cliente autenticado
            String username = authentication.getName();
            // Buscar al cliente por su email
            Optional<Client> optionalClient = clientRepository.findByEmail(username);
            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                ClientDTO clientDTO = new ClientDTO(client);
                return ResponseEntity.ok(clientDTO);
            } else {
                // El cliente no fue encontrado
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        Optional<Client> optionalClient = clientRepository.findByEmail(email);

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (optionalClient.isPresent()) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        //Crea nuevo client
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);

        // Crea nueva account

        Account account = new Account();
        account.setOwner(client);

        // Genera un número de cuenta aleatorio que comienza con "VIN"
        Random rand = new Random();
        String accountNumber = "VIN-" + (rand.nextInt(90000000) + 10000000);
        account.setNumber(accountNumber);
        // Guarda la cuenta a través del repositorio
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {

        LocalDateTime date = LocalDateTime.now();
        String username = authentication.getName();
        Optional<Client> optionalClient = clientRepository.findByEmail(username);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            // Comprueba si el cliente ya tiene 3 cuentas

            if (client.getAccounts().size() >= 3) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            // Crea una nueva cuenta
            Account account = new Account();
            account.setOwner(client); //Recordar que en mi modelo de account se utiliza owner y no client

            // Genera un número de cuenta aleatorio que comienza con "VIN"
            Random rand = new Random();
            String accountNumber = "VIN-" + (rand.nextInt(90000000) + 10000000);
            account.setNumber(accountNumber);
            account.setCreationDate(date);

            // Guarda la cuenta a través del repositorio
            accountRepository.save(account);

            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam Card.CardType cardType, @RequestParam Card.CardColor cardColor) {
        String username = authentication.getName();
        //guarda el cliente ya que estamos trabajando con el optional
        Optional<Client> optionalClient = clientRepository.findByEmail(username);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            // Comprueba si el cliente ya tiene 3 tarjetas del tipo especificado
            long count = client.getCards().stream().filter(card -> card.getType() == cardType).count();
            if (count >= 3) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ya posee 3 tarjetas");
            }

            // Crea una nueva tarjeta

            Card card = new Card();
            card.setClient(client);
            card.setType(cardType);
            card.setColor(cardColor);

            // Genera un número de tarjeta aleatorio con 4 secciones de 4 números
            Random rand = new Random();

            String cardNumber = getCardNumber(rand);
            card.setNumber(cardNumber);

            // Genera un CVV aleatorio de 3 dígitos

            String cvv = CardUtils.getCvv(rand);
            card.setCVV(cvv);

            // Establece el titular de la tarjeta con el nombre y apellido del cliente
            card.setCardHolder(client.getFirstName() + " " + client.getLastName());

            // Establece la fecha de inicio y vencimiento de la tarjeta
            LocalDate now = LocalDate.now();
            card.setFromDate(now);
            card.setThruDate(now.plusYears(5));

            // Guarda la tarjeta a través del repositorio
            cardRepository.save(card);

            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el recurso solicitado");
        }
    }




    @GetMapping("/clients/current/accounts")
    public ResponseEntity<List<AccountDTO>> getCurrentClientAccounts(Authentication authentication) {
        String username = authentication.getName();
        Optional<Client> optionalClient = clientRepository.findByEmail(username);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            List<Account> accounts = new ArrayList<>(client.getAccounts());

            // Crear una lista de objetos AccountDTO
            List<AccountDTO> accountDTOs = accounts.stream()
                    .map(AccountDTO::new)
                    .collect(Collectors.toList());

            // Devolver ResponseEntity con la lista de AccountDTOs
            return ResponseEntity.ok(accountDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }







}


