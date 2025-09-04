package com.gipit.bookshop_backend.controller;

import com.gipit.bookshop_backend.dto.Book.BookDTO;
import com.gipit.bookshop_backend.dto.Book.BookSummaryDTO;
import com.gipit.bookshop_backend.dto.request.CreateBookRequest;
import com.gipit.bookshop_backend.dto.response.ApiResponse;
import com.gipit.bookshop_backend.services.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ApiResponse<Map<String,Object>> getAllBook(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        var rs= bookService.getAllBooks(page,size);
        Map<String, Object> map= new HashMap<>();
        map.put("content",rs.getContent());
        map.put("currentPage",rs.getNumber());
        map.put("totalPages",rs.getTotalPages());
        map.put("totalCount",rs.getTotalElements());
        map.put("size",rs.getSize());
        return ApiResponse.<Map<String,Object>>builder()
                .message("Success")
                .data(map)
                .build();
    }
    @PostMapping
    public ApiResponse<BookDTO> createBook(@RequestBody CreateBookRequest createBookRequest){
        return ApiResponse.<BookDTO>builder()
                .message("Success")
                .data(bookService.createBook(createBookRequest))
                .build();
    }
    @GetMapping("/{bookID}")
    public ApiResponse<BookDTO> getBookById(@PathVariable int bookID){
        return ApiResponse.<BookDTO>builder()
                .message("Success")
                .data(bookService.getBookById(bookID))
                .build();
    }
}
