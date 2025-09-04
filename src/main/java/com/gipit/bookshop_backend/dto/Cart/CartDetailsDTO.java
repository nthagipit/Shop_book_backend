package com.gipit.bookshop_backend.dto.Cart;

import com.gipit.bookshop_backend.dto.Book.BookDTO;
import com.gipit.bookshop_backend.dto.Book.BookSummaryDTO;
import com.gipit.bookshop_backend.models.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailsDTO {
    private int cartDetailsID;
    private int quantity;
    private double sellPrice;
    private boolean ischecked;
    private BookSummaryDTO book;
}
