package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.Author;
import com.gipit.bookshop_backend.repositories.AuthorRepository;
import com.gipit.bookshop_backend.services.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.AUTHOR_NOT_FOUND));
    }
}
