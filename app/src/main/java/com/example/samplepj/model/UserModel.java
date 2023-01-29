package com.example.samplepj.model;

public class UserModel {
    private int userId;
    private String userName;
    private String password;

    public UserModel(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
