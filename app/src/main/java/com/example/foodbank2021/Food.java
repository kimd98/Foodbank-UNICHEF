package com.example.foodbank2021;

public class Food {
    private String name;
    private String amount;
    private String donor;
    private String recipient;
    private String expireDate;
    private String fridge;

    public Food (String name, String amount, String donor, String recipient,
                 String expireDate, String fridge) {
        name = this.name;
        amount = this.amount;
        donor = this.donor;
        recipient = this.recipient;
        expireDate = this.expireDate;
        fridge = this.fridge;
    }

    public Food () {

    }

    public String getName() {
        return this.name;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getDonator() {
        return this.donor;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getExpireDate() {
        return this.expireDate;
    }

    public String getFridgeID() {
        return this.fridge;
    }
}
