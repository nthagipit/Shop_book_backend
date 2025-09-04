package com.gipit.bookshop_backend.mapper;

import com.gipit.bookshop_backend.dto.Cart.CartDetailsDTO;
import com.gipit.bookshop_backend.models.CartDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartDetailsMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CartDetailsDTO toCartDetailsDTO(CartDetails cartDetails) {
        return modelMapper.map(cartDetails, CartDetailsDTO.class);
    }
}
