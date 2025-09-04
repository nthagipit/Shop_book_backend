package com.gipit.bookshop_backend.dto.Book;

import com.gipit.bookshop_backend.dto.Author.AuthorDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private BookSummaryDTO bookSummaryDTO;
    private String description;
    private String ISBN;
    private int quantity;
}
