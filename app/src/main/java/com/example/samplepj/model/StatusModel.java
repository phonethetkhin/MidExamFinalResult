package com.example.samplepj.model;

public class StatusModel {
    private int statusId;
    private String userName;
    private String status;

    public StatusModel(int statusId, String userName, String status) {
        this.statusId = statusId;
        this.userName = userName;
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getUserName() {
        return userName;
    }

    public String getStatus() {
        return status;
    }
}
