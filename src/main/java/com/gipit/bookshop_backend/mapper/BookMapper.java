package com.gipit.bookshop_backend.mapper;

import com.gipit.bookshop_backend.dto.Book.BookDTO;
import com.gipit.bookshop_backend.dto.Book.BookSummaryDTO;
import com.gipit.bookshop_backend.models.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    @Autowired
    private ModelMapper modelMapper;

    public BookSummaryDTO toSummary(Book book) {
        return modelMapper.map(book, BookSummaryDTO.class);
    }
    public BookDTO toDTO(Book book) {
        BookDTO bookDTO= modelMapper.map(book, BookDTO.class);
        bookDTO.setBookSummaryDTO(toSummary(book));
        return bookDTO;
    }
}
