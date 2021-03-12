package com.example.foodbank2021;

public class User {

    public String firstName, lastName, email, as, verified;

    public User(String firstName, String lastName, String email, String as, String verified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.as = as;
        this.verified = verified;
    }

    public String getEmail() {
        return this.email;
    }
}
