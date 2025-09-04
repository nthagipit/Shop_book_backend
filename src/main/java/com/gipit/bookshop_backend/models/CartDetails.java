package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"book", "cart"})
@Table(name="cartdetails")
public class CartDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cardetails_id")
    private int cartDetailsID;
    @Column(name="quantity")
    private int quantity;
    @Column(name="sell_price")
    private double sellPrice;
    @Column(name="ischecked")
    private boolean ischecked;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="book_id")
    private Book book;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="cart_id")
    private Cart cart;
}
