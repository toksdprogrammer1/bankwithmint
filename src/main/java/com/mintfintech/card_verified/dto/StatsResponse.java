package com.mintfintech.card_verified.dto;



import java.io.Serializable;
import java.util.Map;

public class StatsResponse implements Serializable {
    private boolean success;
    private Map<String, Long> payload;
    private int start;
    private int limit;
    private long size;

    public StatsResponse(boolean success, Map<String, Long> payload, int start, int limit, long size) {
        this.success = success;
        this.payload = payload;
        this.start = start;
        this.limit = limit;
        this.size = size;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Long> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Long> payload) {
        this.payload = payload;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}