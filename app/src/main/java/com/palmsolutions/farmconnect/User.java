package com.palmsolutions.farmconnect;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String uuid;
    public String username;
    public String email;
    public String password;
    public String accountType;
    public String name;
    public String city;
    public String userImageUrl;

    public User(String uuid, String username, String email, String password, String accountType, String name, String city) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.name = name;
        this.city = city;
    }

    public User(String uuid, String username, String email, String password, String accountType) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = "";
        this.accountType = accountType;
    }

    public User(String uuid, String username, String email, String password, String accountType, String name, String city, String userImageUrl) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.name = name;
        this.city = city;
        this.userImageUrl = userImageUrl;
    }

    public Map<String, Object> to_Map(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("username", username);
        result.put("email", email);
        result.put("password", password);
        result.put("name", name);
        result.put("city", city);
        result.put("accountType", accountType);
        result.put("user_pic_url", userImageUrl);

        return result;
    }

    public User() {
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}