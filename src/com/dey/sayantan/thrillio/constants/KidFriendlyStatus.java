package com.dey.sayantan.thrillio.constants;

public enum KidFriendlyStatus {
    APPROVED("Approved"),
    REJECTED("Rejected"),
    UNKNOWN("Unknown");

    private final String status;
    private KidFriendlyStatus(String status) {this.status = status;}
    public String getStatus(){return this.status;}
    }
