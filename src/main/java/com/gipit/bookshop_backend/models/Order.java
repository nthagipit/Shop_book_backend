package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
@Entity
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private int orderID;

    @Column(name="order_date")
    private Date orderDate;

    @Column(name="total_prices")
    private double totalPrices;

    @Column(name="totalproduct_prices")
    private double totalProductPrices;

    @Column(name="purchase_address",length=512)
    private String purchaseAddress;

    @Column(name="delivery_address",length=512)
    private String deliveryAddress;

    @Column(name="status_order",length=512)
    private String statusOrder;

    @Column(name="delivery_date")
    private Date deliveryDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<OrderDetails> listOrderDetails;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id",nullable=false)
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="proccedcheckout_id")
    private ProccedCheckOut proccedCheckOut;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="procceddelivery_id")
    private ProccedDelivery proccedDelivery;

}
