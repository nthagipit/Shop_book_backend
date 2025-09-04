package com.gipit.bookshop_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name="book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private int bookID;
    @Column(name="book_name",length=256)
    private String bookName;
    @Column(name="isbn", length=256)
    @JsonProperty("ISBN")
    private String ISBN;
    @Column(name="listed_price")
    private double listedPrice;
    @Column(name="sell_price")
    private double sellPrice;
    @Column(name="quantity")
    private int quantity;
    @Column(name="description", columnDefinition = "text")
    private String description;
    @Column(name="average_rank")
    private double averageRank;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="author_id" )
//    @RestResource(exported = false)
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(name="book_category",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))
    private List<Category> listCategories;

    @OneToMany(mappedBy="book",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Image> listImages;

    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Commentation> listCommentations;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name="book_wishlist",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="wishlist_id")
    )
    private List<WishList>  listWishlists;

    @OneToMany(mappedBy="book",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH,CascadeType.REFRESH})
    private List<CartDetails> listCartDetails;

    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH, CascadeType.REMOVE})
    private List<OrderDetails> listOrderDetails;
}
