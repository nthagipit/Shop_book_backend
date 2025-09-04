package com.gipit.bookshop_backend.dto.Book;

import com.gipit.bookshop_backend.dto.Author.AuthorDTO;
import com.gipit.bookshop_backend.dto.Author.AuthorSummaryDTO;
import com.gipit.bookshop_backend.dto.Image.ImageDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookSummaryDTO {
    private int bookID;
    private String bookName;
    private double sellPrice;
    private double listedPrice;
    private double averageRank;
    private AuthorDTO author;
    private List<ImageDTO> listImages;
}
