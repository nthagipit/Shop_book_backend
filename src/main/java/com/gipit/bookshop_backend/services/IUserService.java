package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.dto.request.CreateUserRequest;
import com.gipit.bookshop_backend.dto.request.UpdateUserRequest;
import com.gipit.bookshop_backend.models.User;

import java.util.Optional;

public interface IUserService {
    User getUserByUsername(String username);
    Optional<User> findByEmail(String email);
    User saveUser(User user);
    User createUser(CreateUserRequest createUserRequest);
    void sendEmailVerification(String to, String activeCode);
    boolean activeAccount(String activeCode);
    User updateUser(int userID,UpdateUserRequest updateUserRequest);
    User getUserById(int userID);
}
