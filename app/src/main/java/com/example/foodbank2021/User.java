package com.example.foodbank2021;

public class User {

    public String firstName, lastName, email, as, verified;
    public Integer penalty;

    public User(String firstName, String lastName, String email, String as, String verified,Integer penalty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.as = as;
        this.verified = verified;
        this.penalty=penalty;
    }

    public User() {}

    public String getEmail() {
        return this.email;
    }
    public String getName() {
        String first = this.firstName;
        String last = this.lastName;
        String name = first.concat(last);
        return name;
    }
}
