package com.gipit.bookshop_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private int categoryId;

    @Column(name="category_name",length=256)
    private String categoryName;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.PERSIST,CascadeType.MERGE,
                           CascadeType.REFRESH,CascadeType.DETACH})
    @JoinTable(name="book_category",
               joinColumns = @JoinColumn(name="category_id"),
               inverseJoinColumns = @JoinColumn(name="book_id")
              )
    @JsonIgnore
    private List<Book> listBooks;
}
