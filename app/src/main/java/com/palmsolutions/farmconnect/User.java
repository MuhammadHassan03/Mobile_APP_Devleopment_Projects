package com.palmsolutions.farmconnect;

public class User {
    public String uuid;
    public String username;
    public String email;
    public String password;
    public String accountType;


    public User(String uuid, String username, String email, String password, String accountType) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }
    public User(){

    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

