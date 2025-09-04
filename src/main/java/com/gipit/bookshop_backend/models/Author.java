package com.gipit.bookshop_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="author_id")
    private int authorID;
    @Column(name="author_name", length=256)
    private String authorName;
    @Column(name="gender",length=10)
    private String gender;
    @Column(name="date_of_birth")
    private Date dateOfBirth;
    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})

    private List<Book> listBooks;
}
