package com.gipit.bookshop_backend.dto.Author;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorSummaryDTO {
    private int authorID;
    private String authorName;
}
