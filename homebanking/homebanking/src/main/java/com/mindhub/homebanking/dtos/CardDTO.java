package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;

import java.time.LocalDate;

public class CardDTO {

    private Long id;
    private String number;
    private String cardHolder;
    private Card.CardType type;
    private Card.CardColor color;
    private String CVV;
    private LocalDate thruDate;
    private LocalDate fromDate;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.number = card.getNumber();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.CVV = card.getCVV();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Card.CardType getType() {
        return type;
    }

    public void setType(Card.CardType type) {
        this.type = type;
    }

    public Card.CardColor getColor() {
        return color;
    }

    public void setColor(Card.CardColor color) {
        this.color = color;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
}
