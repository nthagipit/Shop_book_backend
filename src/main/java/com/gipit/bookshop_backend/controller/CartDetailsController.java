package com.gipit.bookshop_backend.controller;

import com.gipit.bookshop_backend.dto.Cart.CartDetailsDTO;
import com.gipit.bookshop_backend.dto.request.CartDetailsRequest;
import com.gipit.bookshop_backend.services.impl.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cart-details")
public class CartDetailsController {
    @Autowired
    private CartDetailService cartDetailService;

    @PatchMapping()
    public CartDetailsDTO updateCartDetails(@RequestBody CartDetailsRequest request) {
        return cartDetailService.updateCartDetail(request);
    }
}
