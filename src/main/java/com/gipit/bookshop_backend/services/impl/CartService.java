package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.dto.Cart.CartDTO;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.mapper.CartMapper;
import com.gipit.bookshop_backend.models.Cart;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.repositories.CartRepository;
import com.gipit.bookshop_backend.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartDTO getCarts(int userID) {
        User user= userService.getUserById(userID);
        if(user==null)
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        Cart cart= cartRepository.findByUser_UserID(userID);

        if(cart==null){
            Cart  cartNew= Cart.builder()
                    .user(user)
                    .deliveryAddress(user.getDeliveryAddress())
                    .purchaseAddress(user.getPurchaseAddress())
                    .totalPrices(0)
                    .createDate(new Date(System.currentTimeMillis()))
                    .build();
                cartRepository.save(cartNew);
            return cartMapper.toCartDTO(cartNew);
        }

        return cartMapper.toCartDTO(cart);
    }

    @Override
    public Cart getCartByID(int userID) {
        User user= userService.getUserById(userID);
        if(user==null)
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        Cart cart= cartRepository.findByUser_UserID(userID);

        if(cart==null){
            Cart  cartNew= Cart.builder()
                    .user(user)
                    .deliveryAddress(user.getDeliveryAddress())
                    .purchaseAddress(user.getPurchaseAddress())
                    .totalPrices(0)
                    .createDate(new Date(System.currentTimeMillis()))
                    .build();
            return cartRepository.save(cartNew);
        }

        return cart;
    }
}
