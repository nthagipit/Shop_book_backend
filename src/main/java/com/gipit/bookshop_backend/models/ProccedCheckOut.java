package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name="proccedcheckout")
public class ProccedCheckOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="proccedcheckout_id")
    private int proccedCheckOutID;

    @Column(name="proccedcheckout_name",length=512)
    private String proccedCheckOutName;

    @Column(name="description",columnDefinition = "text")
    private String description;

    @Column(name="checkout_fee")
    private double checkOutFee;

    @OneToMany(mappedBy = "proccedCheckOut",fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    private List<Order> listOrders;
}
