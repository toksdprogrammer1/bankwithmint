package com.mintfintech.card_verified.dto;



import java.io.Serializable;

public class Response implements Serializable {
    private boolean success;
    private Object payload;

    public Response(boolean success, Object payload) {
        this.success = success;
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}