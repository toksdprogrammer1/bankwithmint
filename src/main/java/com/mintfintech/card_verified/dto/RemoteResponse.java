package com.mintfintech.card_verified.dto;

public class RemoteResponse {
    private int code;
    private String responseBody;

    public RemoteResponse(){}

    public RemoteResponse(int code, String responseBody){
        this.code = code;
        this.responseBody = responseBody;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
