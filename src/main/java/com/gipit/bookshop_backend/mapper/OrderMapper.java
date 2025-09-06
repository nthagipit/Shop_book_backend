package com.gipit.bookshop_backend.mapper;

import com.gipit.bookshop_backend.dto.Order.OrderDTO;
import com.gipit.bookshop_backend.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO toOrderDTO(Order order) {
        return modelMapper.map( order,OrderDTO.class);
    }
}
