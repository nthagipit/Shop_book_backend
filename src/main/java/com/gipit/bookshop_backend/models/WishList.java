package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name="wishlist")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wishlist_id")
    private int wishListID;

    @Column(name="wishlist_name",length=256)
    private String wishListName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinTable(name="book_wishlist",joinColumns = @JoinColumn(name="wishlist_id"),inverseJoinColumns = @JoinColumn(name="book_id"))
    private List<Book> listBooks;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="user_id",nullable=false)
    private User user;
}
