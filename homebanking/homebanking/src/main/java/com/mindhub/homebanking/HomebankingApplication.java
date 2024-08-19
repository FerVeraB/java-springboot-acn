package com.mindhub.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	//@Autowired
	//private PasswordEncoder passwordEncoder;


	@Bean
	public CommandLineRunner initData(ClientRepository clientrepository, AccountRepository accountrepository, TransactionRepository transactionRepository,
									  LoanRepository loanrepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers
/*
			List<String> hipo1 = List.of("12", "24", "36","48","60");
			List<String> hipo2 = List.of("6", "12", "24");
			List<String> hipo3 = List.of("6", "12", "24","36");

			Client client1 = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("12345"));
			Client client2 = new Client("Ryu", "UwU","ryuwu@mindhub.com",passwordEncoder.encode("12345"));
			Client client3 = new Client("Camila", "Gonzalez","camgon@mindhub.com",passwordEncoder.encode("12345"));


			//Client owner = clientrepository.findById(1L);
			Long num = 5000L;
			LocalDateTime date = LocalDateTime.now();

			//accountrepository.save(new Account(num,date,5000,client1));

			Account account1 = new Account("VIN001",date,5000,client1);
			Account account2 = new Account("VIN002",date.plusDays(1),7500, client1);
			Account account3 = new Account("VIN003",date,9000,client2);
			Account account4 = new Account("VIN004",date.plusDays(1),6500,client2);

			Transaction transaction1 = new Transaction(Transaction.TransactionType.Debito, account1, date, 1000, "Pagos");
			Transaction transaction2 = new Transaction(Transaction.TransactionType.Credito, account1, date.plusDays(1), 1000, "Pagos");
			Transaction transaction3 = new Transaction(Transaction.TransactionType.Debito, account2, date, 1000, "Pagos");
			Transaction transaction4 = new Transaction(Transaction.TransactionType.Credito, account2, date.plusDays(1), 1900, "Pagos");

			Loan loan1 = new Loan("Hipotecario", 500000, hipo1);
			Loan loan2 = new Loan("Personal", 100000, hipo2);
			Loan loan3 = new Loan("Automotriz", 300000, hipo3);

			ClientLoan clientLoan1 = new ClientLoan(400000, "60", client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000, "12", client1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000, "24", client2, loan1);
			ClientLoan clientLoan4 = new ClientLoan(400000, "36", client2, loan2);

			Card card1= new Card();
			Card card2= new Card();
			Card card3= new Card();

			card1.setCardHolder(client1.getFirstName() + " " + client1.getLastName());
			client1.addCard(card1);
			card1.setType(Card.CardType.DEBIT);
			card1.setColor(Card.CardColor.GOLD);
			card1.setFromDate(LocalDate.now());
			card1.setThruDate(LocalDate.now().plusYears(5));
			card1.setCVV(String.format("%03d", new Random().nextInt(999)));
			card1.setNumber("1234 5678 9012 3456");

			card2.setCardHolder(client1.getFirstName() + " " + client1.getLastName());
			client1.addCard(card2);
			card2.setType(Card.CardType.CREDIT);
			card2.setColor(Card.CardColor.TITANIUM);
			card2.setFromDate(LocalDate.now());
			card2.setThruDate(LocalDate.now().plusYears(5));
			card2.setCVV(String.format("%03d", new Random().nextInt(999)));
			card2.setNumber("1235 4678 9612 3756");

			card3.setCardHolder(client2.getFirstName() + " " + client2.getLastName());
			client2.addCard(card3);
			card3.setType(Card.CardType.CREDIT);
			card3.setColor(Card.CardColor.SILVER);
			card3.setFromDate(LocalDate.now());
			card3.setThruDate(LocalDate.now().plusYears(5));
			card3.setCVV(String.format("%03d", new Random().nextInt(999)));
			card3.setNumber("1735 4678 3612 3766");

			clientrepository.save(client1);
			clientrepository.save(client2);
			clientrepository.save(client3);
			accountrepository.save(account1);
			accountrepository.save(account2);
			accountrepository.save(account3);
			accountrepository.save(account4);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			loanrepository.save(loan1);
			loanrepository.save(loan2);
			loanrepository.save(loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3); */


		};

	}






	}


