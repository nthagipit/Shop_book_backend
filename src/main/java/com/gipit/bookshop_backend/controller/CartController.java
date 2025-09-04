package com.gipit.bookshop_backend.controller;

import com.gipit.bookshop_backend.dto.Cart.CartDTO;
import com.gipit.bookshop_backend.models.Cart;
import com.gipit.bookshop_backend.services.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public List<CartDTO> getCarts(@RequestBody int userID) {
        return List.of(cartService.getCarts(userID));
    }
}
