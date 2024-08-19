package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){

        Random rand = new Random();
        String cardNumber = CardUtils.getCardNumber(rand);

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }
}
