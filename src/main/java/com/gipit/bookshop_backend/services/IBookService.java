package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.dto.Book.BookDTO;
import com.gipit.bookshop_backend.dto.Book.BookSummaryDTO;
import com.gipit.bookshop_backend.dto.request.CreateBookRequest;
import org.springframework.data.domain.Page;




public interface IBookService {
    Page<BookSummaryDTO> getAllBooks(int page, int size);
    BookDTO createBook(CreateBookRequest createBookRequest);
    BookDTO getBookById(int id);
}
