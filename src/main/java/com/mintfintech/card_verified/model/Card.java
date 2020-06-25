package com.mintfintech.card_verified.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "card")
public class Card implements Serializable {

    private long id;
    private String cardNo;
    private String scheme;
    private String type;
    private String bank;
    private long hit;

    public Card(String scheme, String type, String bank, boolean success) {

        this.scheme = scheme;
        this.type = type;
        this.bank = bank;
    }

    public Card() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

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

    public long getHit() {
        return hit;
    }

    public void setHit(long hit) {
        this.hit = hit;
    }

}
