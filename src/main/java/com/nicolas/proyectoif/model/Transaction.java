package com.nicolas.proyectoif.model;

public class Transaction {
    private String cardNumber;
    private String timeStamp;
    private Integer balance;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return  cardNumber + "," + timeStamp + "," + balance;
    }
}
