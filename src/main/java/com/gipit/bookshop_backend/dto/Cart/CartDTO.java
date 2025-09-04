package com.gipit.bookshop_backend.dto.Cart;

import com.gipit.bookshop_backend.models.CartDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private int cartID;
    private Date createDate;
    private double totalPrices;
    private String purchaseAddress;
    private String deliveryAddress;
    private List<CartDetailsDTO> listCartDetails;
}
