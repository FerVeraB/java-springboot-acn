package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;


    //Loans
    @Test
    public void testExistLoans() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test
    public void testExistPersonalLoan() {
        Optional<Loan> personalLoan = loanRepository.findByName("Personal");
        assertThat(personalLoan.isPresent(), is(true));
    }

    //Accounts

    @Test
    public void testFindAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void testFindAccountByNumber() {
        Optional<Account> account = accountRepository.findByNumber("123456");
        assertThat(account.isPresent(), is(true));
    }

    //Cards

    @Test
    public void testFindAllCards() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    public void testFindCardByNumber() {
        Optional<Card> card = cardRepository.findByNumber("987654");
        assertThat(card.isPresent(), is(true));
    }

    //Client

    @Test
    public void testFindAllClients() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void testFindClientByEmail() {
        Optional<Client> client = clientRepository.findByEmail("john.doe@example.com");
        assertThat(client.isPresent(), is(true));
    }
}
