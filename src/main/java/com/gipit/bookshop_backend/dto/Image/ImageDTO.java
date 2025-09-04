package com.gipit.bookshop_backend.dto.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private String imageName;
    private String link;
    @JsonProperty("isIcon")
    private boolean isIcon;
    private String dataImage;
}
