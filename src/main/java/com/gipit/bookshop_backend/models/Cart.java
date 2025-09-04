package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
@Entity
@Data
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id")
    private int cartID;
    @Column(name="create_date")
    private Date createDate;
    @Column(name="total_prices")
    private double totalPrices;
    @Column(name="purchase_address",length=512)
    private String purchaseAddress;
    @Column(name="delivery_address",length=512)
    private String deliveryAddress;
    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartDetails> listCartDetails;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="user_id")
    private User user;
}
