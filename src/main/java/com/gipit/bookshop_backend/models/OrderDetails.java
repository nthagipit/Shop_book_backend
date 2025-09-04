package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="orderdetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderdetails_id")
    private long orderDetailsID;

    @Column(name="quantity")
    private int quantity;

    @Column(name="sell_price")
    private double sellPrice;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="book_id",nullable=false)
    private Book book;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="order_id",nullable=false)
    private Order order;
}
