package com.gipit.bookshop_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gipit.bookshop_backend.dto.Image.ImageDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookRequest {
    private String bookName;
    private String description;
    @JsonProperty("ISBN")
    private String ISBN;
    private double sellPrice;
    private int quantity;
    private int  authorID;
    private List<ImageDTO> images;
}
