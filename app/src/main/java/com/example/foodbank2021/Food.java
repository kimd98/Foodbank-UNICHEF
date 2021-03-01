package com.example.foodbank2021;

public class Food {
    private String mName;
    private String mAmount;
    private String mDonator;
    private String mReceiver;
    private String mExpireDate;
    private String mFridge;

    public Food (String name, String amount, String donator, String receiver,
                 String expireDate, String fridgeID) {
        mName = name;
        mAmount = amount;
        mDonator = donator;
        mReceiver = receiver;
        mExpireDate = expireDate;
        mFridge = fridgeID;
    }

    public String getName() {
        return mName;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getDonator() {
        return mDonator;
    }

    public String getReceiver() {
        return mReceiver;
    }

    public String getExpireDate() {
        return mExpireDate;
    }

    public String getFridgeID() {
        return mFridge;
    }
}
