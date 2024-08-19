package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.type.LocalDateType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    //enum para credito y debito
    public enum TransactionType{
        Debito,
        Credito
    }

    private Long id;
    private Transaction.TransactionType type;
    private LocalDateTime creationDate;
    private int amount;
    private String description;

    //Soy un constructor
    public TransactionDTO(){

    };

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type=transaction.getType();
        this.creationDate=transaction.getCreationDate();
        this.amount=transaction.getAmount();
        this.description=transaction.getDescription();
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction.TransactionType getType() {
        return type;
    }

    public void setType(Transaction.TransactionType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }


}
