package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import java.util.Random;

public class CardUtils {

    public static String getCvv(Random rand) {
        return String.valueOf(rand.nextInt(900) + 100);
    }
    public static String getCardNumber(Random rand) {
        return (rand.nextInt(9000) + 1000) + "-" + (rand.nextInt(9000) + 1000) + "-" + (rand.nextInt(9000) + 1000) + "-" + (rand.nextInt(9000) + 1000);
    }


}
