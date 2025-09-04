package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name="procced_delivery")
public class ProccedDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="procceddelivery_id")
    private int proccedDeliveryID;

    @Column(name="procceddelivery_name", length=512)
    private String proccedDeliveryName;

    @Column(name="description" ,columnDefinition = "text")
    private String description;

    @Column(name="delivery_fee")
    private double deliveryFee;

    @OneToMany(mappedBy = "proccedDelivery",fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Order> listOrders;
}
