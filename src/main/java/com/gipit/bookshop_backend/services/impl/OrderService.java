package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.dto.Order.OrderDTO;
import com.gipit.bookshop_backend.dto.request.OrderRequest;
import com.gipit.bookshop_backend.mapper.OrderMapper;
import com.gipit.bookshop_backend.models.*;
import com.gipit.bookshop_backend.repositories.OrderRepository;
import com.gipit.bookshop_backend.services.IOrderService;
import com.gipit.bookshop_backend.utils.StatusOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private ProccedDeliveryService proccedDeliveryService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO createOrderFromCart(int userID, OrderRequest orderRequest) {
        User user = userService.getUserById(userID);
        Cart cart=cartService.getCartByID(userID);
        ProccedDelivery proccedDelivery=proccedDeliveryService.getProccedDeliveryById(userID);

        Order order= Order.builder()
                .orderDate(new Date(System.currentTimeMillis()))
                .deliveryDate(new Date(System.currentTimeMillis()))
                .statusOrder(StatusOrder.PENDING.toString())
                .user(user)
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .proccedDelivery(proccedDelivery)
                .purchaseAddress(orderRequest.getPurchaseAddress())
                .build();
        List<OrderDetails> list= new ArrayList<>();
        double totalProduct=0;
        List<CartDetails> checkedItems= cart.getListCartDetails()
                .stream()
                .filter(CartDetails::isIschecked)
                .toList();
        for(CartDetails  item : checkedItems){
                OrderDetails orderDetails=OrderDetails.builder()
                        .quantity(item.getQuantity())
                        .sellPrice(item.getSellPrice())
                        .book(item.getBook())
                        .order(order)
                        .build();
                list.add(orderDetails);
                totalProduct+=item.getQuantity()*item.getSellPrice();
                cartDetailService.deleteCartDetail(item.getCartDetailsID());
        }
        //Xóa trong collection để tránh Hibernate giữ reference là ko xóa đc
        cart.getListCartDetails().removeAll(checkedItems);
        order.setTotalPrices(totalProduct);
        order.setTotalProductPrices(totalProduct+proccedDelivery.getDeliveryFee());
        order.setListOrderDetails(list);
        ;
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }
//    @Override
//    @Transactional
//    public OrderDTO createOrderFromCart(int userID, OrderRequest orderRequest) {
//        User user = userService.getUserById(userID);
//        Cart cart = cartService.getCartByID(userID);
//
//        Order order = Order.builder()
//                .orderDate(new Date(System.currentTimeMillis()))
//                .deliveryDate(new Date(System.currentTimeMillis()))
//                .statusOrder(StatusOrder.PENDING.toString())
//                .user(user)
//                .deliveryAddress(orderRequest.getDeliveryAddress())
//                .purchaseAddress(orderRequest.getPurchaseAddress())
//                .build();
//
//        List<OrderDetails> list = new ArrayList<>();
//        double totalProduct = 0;
//
//        // Dùng iterator để xóa an toàn trong collection
//        var iterator = cart.getListCartDetails().iterator();
//        while (iterator.hasNext()) {
//            CartDetails item = iterator.next();
//            if (item.isIschecked()) {
//                OrderDetails orderDetails = OrderDetails.builder()
//                        .quantity(item.getQuantity())
//                        .sellPrice(item.getSellPrice())
//                        .book(item.getBook())
//                        .order(order)
//                        .build();
//                list.add(orderDetails);
//                totalProduct += item.getQuantity() * item.getSellPrice();
//
//                // Xóa khỏi cart, orphanRemoval sẽ lo phần delete trong DB
//                iterator.remove();
//            }
//        }
//
//        order.setListOrderDetails(list);
//
//        return orderMapper.toOrderDTO(orderRepository.save(order));
//    }
}
