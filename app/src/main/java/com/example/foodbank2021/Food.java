package com.example.foodbank2021;

public class Food {
    private String name;
    private String amount;
    private String donor;
    private String recipient;
    private String expiryDate;
    private String fridge;

    public Food (String name, String amount, String donor, String recipient,
                 String expiryDate, String fridge) {
        this.name = name;
        this.amount = amount;
        this.donor = donor;
        this.recipient = recipient;
        this.expiryDate = expiryDate;
        this.fridge = fridge;
    }

    public Food () {
        // empty constructor for firebase
    }

    public String getName() {
        return this.name;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getDonor() {
        return this.donor;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getFridge() {
        return this.fridge;
    }

}
