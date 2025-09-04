package com.gipit.bookshop_backend.services;

public interface IMailService {
    public void sendMail(String email, String to, String subject, String body);
}
