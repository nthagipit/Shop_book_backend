package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.dto.Book.BookDTO;
import com.gipit.bookshop_backend.dto.Book.BookSummaryDTO;
import com.gipit.bookshop_backend.dto.request.CreateBookRequest;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.mapper.BookMapper;
import com.gipit.bookshop_backend.models.Author;
import com.gipit.bookshop_backend.models.Book;
import com.gipit.bookshop_backend.repositories.BookRepository;
import com.gipit.bookshop_backend.services.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
public class BookService implements IBookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BookMapper bookMapper;
    @Override
    public Page<BookSummaryDTO> getAllBooks(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);

        Page<Book> books= bookRepository.findAll(pageable);
        return books.map(book ->bookMapper.toSummary(book));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO createBook(CreateBookRequest createBookRequest) {
        Author author=authorService.getAuthorById(createBookRequest.getAuthorID());
        if(author==null)
            return null;
        Book book=Book.builder()
                .author(author)
                .bookName(createBookRequest.getBookName())
                .ISBN(createBookRequest.getISBN())
                .description(createBookRequest.getDescription())
                .sellPrice(createBookRequest.getSellPrice())
                .listedPrice(createBookRequest.getSellPrice())
                .quantity(createBookRequest.getQuantity())
                .build();
        book=bookRepository.save(book);
        imageService.saveImage(createBookRequest.getImages(),book);
        return bookMapper.toDTO(book);
    }

    @Override
//    @PreAuthorize("hasRole('USER')")
    public BookDTO getBookById(int id) {
        Book book=bookRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.BOOK_NOT_FOUND));
        return bookMapper.toDTO(book);
    }

    @Override
    public Book getBook(int bookID) {
        return bookRepository.findById(bookID).orElseThrow(()->new AppException(ErrorCode.BOOK_NOT_FOUND));
    }

}
