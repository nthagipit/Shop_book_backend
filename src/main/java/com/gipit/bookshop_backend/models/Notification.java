package com.gipit.bookshop_backend.models;

public class Notification {
    private String context;
    public Notification(String context) {
        this.context = context;
    }
    public String getContext() {
        return context;

    }
    public void setContext(String context) {
        this.context = context;
    }
}
