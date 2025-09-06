package com.gipit.bookshop_backend.controller;

import com.gipit.bookshop_backend.dto.Order.OrderDTO;
import com.gipit.bookshop_backend.dto.request.OrderRequest;
import com.gipit.bookshop_backend.dto.response.ApiResponse;
import com.gipit.bookshop_backend.models.UserPrincipal;
import com.gipit.bookshop_backend.services.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ApiResponse<OrderDTO> createOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody OrderRequest orderRequest) {
        return ApiResponse.<OrderDTO>builder()
                .message("Successfully created a new order")
                .data(orderService.createOrderFromCart(userPrincipal.getUser().getUserID(), orderRequest))
                .build();
    }
}
