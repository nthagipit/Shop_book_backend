package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.models.OrderDetails;
import com.gipit.bookshop_backend.repositories.OrderDetailsRepository;
import com.gipit.bookshop_backend.services.IOrderDetailsService;
import com.gipit.bookshop_backend.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService implements IOrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }
}
