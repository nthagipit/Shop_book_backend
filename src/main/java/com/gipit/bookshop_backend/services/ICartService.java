package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.dto.Cart.CartDTO;
import com.gipit.bookshop_backend.models.Cart;

import java.util.List;

public interface ICartService {
    CartDTO getCarts(int userID);
    Cart getCartByID(int userID);
}
