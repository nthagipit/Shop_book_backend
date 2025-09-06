package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.models.OrderDetails;

public interface IOrderDetailsService {
    OrderDetails createOrderDetails(OrderDetails orderDetails);
}
