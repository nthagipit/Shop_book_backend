package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.models.Author;

public interface IAuthorService {
    Author getAuthorById(int id);
}
