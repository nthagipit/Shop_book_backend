package com.gipit.bookshop_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="image")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    private int imageID;

    @Column(name="image_name", length=256)
    private String imageName;

    @Column(name="link")
    private String link;

    @Column(name="is_icon")
    @JsonProperty("isIcon")
    private boolean isIcon;

    @Column(name="image_data" ,columnDefinition = "LONGTEXT")
    @Lob
    private String dataImage;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="book_id")
    private Book book;
}
