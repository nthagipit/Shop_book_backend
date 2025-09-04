package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userID;

    @Column(name="username",length=50)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email",length=100)
    private String email;

    @Column(name="phone", length=20)
    private String phone;

    @Column(name="gender",length=10)
    private String gender;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column(name="purchase_address",length=512)
    private String purchaseAddress;

    @Column(name="delivery_address",length=512)
    private String deliveryAddress;
    @Column(name="active")
    private boolean active ;
    @Column(name="activation_code")
    private String activationCode;

    @Column(name="provider",length=20)
    private String provider;

//    @Column(name="email_verified")
//    private boolean emailVerified;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> listRoles;

    @OneToMany(mappedBy="user",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    private List<Cart> listCarts;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    private List<Order> listOrders;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    private List<WishList> listWishlists;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    private List<Commentation> listCommentations;

}
