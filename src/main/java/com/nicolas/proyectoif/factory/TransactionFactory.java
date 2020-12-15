package com.nicolas.proyectoif.factory;

import com.nicolas.proyectoif.model.Transaction;
import com.nicolas.proyectoif.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TransactionFactory {
    private static final Integer MIN_BAL = 50;
    private static final Integer MAX_BAL = 100000;
    private static final String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String PADDING_MASK = "00000000000000000";
    private static final Integer MAX_CARD_NUMBER = 100000;
    private static final Integer MIN_CARD_NUMBER = 1;
    private static final Integer ROUND_MULTIPLE = 50;
    private static final Integer YEAR = 2020;

    public Transaction createRandomZeroPadding(int month) {
        Transaction transaction = new Transaction();
        transaction.setBalance(getRandomBalance());
        transaction.setTimeStamp(getRandomTimeStamp(month));
        transaction.setCardNumber(getRandomCardNumberZeroPadding());
        return transaction;
    }

    public Transaction createRandomNoPadding(int month) {
        Transaction transaction = new Transaction();
        transaction.setBalance(getRandomBalance());
        transaction.setTimeStamp(getRandomTimeStamp(month));
        transaction.setCardNumber(getRandomCardNumber());
        return transaction;
    }

    private Integer getRandomBalance() {
        Integer randomBalalance = ThreadLocalRandom.current().nextInt(MIN_BAL, MAX_BAL + 1);
        return ROUND_MULTIPLE * (Math.round(randomBalalance / ROUND_MULTIPLE));
    }

    private String getRandomTimeStamp(int month){
        LocalDate startDate = LocalDate.of(YEAR, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(
                startDate.getMonth().length(startDate.isLeapYear()));
        LocalDateTime randomDate = DateUtil.getRandomDateTime(startDate, endDate);
        return randomDate.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    private String getRandomCardNumber() {
        Integer randomCardNumber = ThreadLocalRandom.current().nextInt(MIN_CARD_NUMBER, MAX_CARD_NUMBER);
        return  String.valueOf(randomCardNumber);
    }

    private String getRandomCardNumberZeroPadding() {
        String unpaddedCardNumber = getRandomCardNumber();
        return PADDING_MASK.substring(unpaddedCardNumber.length()) + unpaddedCardNumber;
    }
}
