package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.dto.Order.OrderDTO;
import com.gipit.bookshop_backend.dto.request.OrderRequest;

public interface IOrderService {
    OrderDTO createOrderFromCart(int userID,OrderRequest orderRequest);
}
