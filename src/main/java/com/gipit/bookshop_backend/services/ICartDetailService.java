package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.dto.Cart.CartDetailsDTO;
import com.gipit.bookshop_backend.dto.request.CartDetailsRequest;
import com.gipit.bookshop_backend.dto.request.CreateCartDetailsRequest;

public interface ICartDetailService {
    CartDetailsDTO updateCartDetail(CartDetailsRequest cartDetailsRequest);
    CartDetailsDTO addCartDetail(CreateCartDetailsRequest createCartDetailsRequest);
    boolean deleteCartDetail(int cartDetailsId);
}
