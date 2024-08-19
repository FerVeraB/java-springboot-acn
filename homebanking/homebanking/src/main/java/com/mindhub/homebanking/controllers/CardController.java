package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<CardDTO> getCard(@PathVariable Long id, Authentication authentication) {
        try {
            // Obtener el nombre de usuario (email) del cliente autenticado
            String username = authentication.getName();
            // Buscar al cliente por su email
            Optional<Client> optionalClient = clientRepository.findByEmail(username);
            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                return cardRepository.findById(id)
                        .map(card -> {
                            // Aquí conviertes el account a ClientDTO
                            CardDTO cardDTO = new CardDTO(card);
                            return cardDTO;
                        })
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
            } else {
                // El cliente no fue encontrado
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    @DeleteMapping("/cards/{id}")
    public ResponseEntity<String> deleteCard(Authentication authentication, @PathVariable Long id ) {
        String username = authentication.getName();
        //Optional<Card> card = cardRepository.findById(id);
        Optional<Client> optionalClient = clientRepository.findByEmail(username);
        if (optionalClient.isPresent()){
            Client client = optionalClient.get();
            Set<Card> cards = client.getCards();
            List<Card> findCard = cards.stream()
                    .filter(card -> card.getId() == id)
                    .collect(Collectors.toList());
            if (findCard.isEmpty()){
                return new ResponseEntity<>("Tarjeta no encontrada", HttpStatus.NOT_FOUND);
            }else {
                cardRepository.deleteById(id);
                return new ResponseEntity<>("Tarjeta eliminada", HttpStatus.OK);
            }

        }else {
            return new ResponseEntity<>("Error al Borrar Tarjeta", HttpStatus.NOT_FOUND);
        }


    }




}
