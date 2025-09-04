package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="commentation")
public class Commentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comentation_id")
    private long commentationID;

    @Column(name="rankAverage")
    private double rank;
    @Column(name="content",columnDefinition = "text")
    private String feedBack;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="user_id",nullable=false)
    private User user;
}
