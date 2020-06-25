package com.mintfintech.card_verified.dto;

import java.io.Serializable;

public class CardResponse implements Serializable {
    private String scheme;
    private String type;
    private String bank;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
