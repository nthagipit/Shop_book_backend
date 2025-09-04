package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.dto.Cart.CartDetailsDTO;
import com.gipit.bookshop_backend.dto.request.CartDetailsRequest;
import com.gipit.bookshop_backend.dto.request.CreateCartDetailsRequest;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.mapper.CartDetailsMapper;
import com.gipit.bookshop_backend.models.Book;
import com.gipit.bookshop_backend.models.Cart;
import com.gipit.bookshop_backend.models.CartDetails;
import com.gipit.bookshop_backend.repositories.CartDetailsRepository;
import com.gipit.bookshop_backend.services.ICartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartDetailService implements ICartDetailService {

    @Autowired
    private CartDetailsRepository cartDetailsRepository;

    @Autowired
    private CartDetailsMapper cartDetailsMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Override
    public CartDetailsDTO updateCartDetail(CartDetailsRequest cartDetailsRequest) {
        CartDetails cartDetail= cartDetailsRepository.findByCartDetailsID(cartDetailsRequest.getCartDetailsID());
        if(cartDetail==null){
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);
        }
        cartDetail.setQuantity(cartDetailsRequest.getQuantity());
        cartDetail.setIschecked(cartDetailsRequest.isIschecked());
        CartDetails updatedCartDetail = cartDetailsRepository.save(cartDetail);
        return cartDetailsMapper.toCartDetailsDTO(updatedCartDetail);
    }

    @Override
    public CartDetailsDTO addCartDetail(CreateCartDetailsRequest createCartDetailsRequest) {
        Cart cart= cartService.getCartByID(createCartDetailsRequest.getUserID());
        Book book=bookService.getBook(createCartDetailsRequest.getBookID());
        CartDetails cartDetail= CartDetails.builder()
                .cart(cart)
                .book(book)
                .quantity(1)
                .ischecked(false)
                .sellPrice(book.getSellPrice())
                .build();
        return cartDetailsMapper.toCartDetailsDTO(cartDetailsRepository.save(cartDetail));
    }

    @Override
    public boolean deleteCartDetail(int cartDetailsId) {
        if(cartDetailsRepository.existsById(cartDetailsId)){
            cartDetailsRepository.deleteById(cartDetailsId);
            return true;
        }
        return false;
    }
}
