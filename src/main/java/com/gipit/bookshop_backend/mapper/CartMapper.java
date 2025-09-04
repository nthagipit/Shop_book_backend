package com.gipit.bookshop_backend.mapper;

import com.gipit.bookshop_backend.dto.Cart.CartDTO;
import com.gipit.bookshop_backend.models.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CartDTO toCartDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }
}
