package com.gipit.bookshop_backend.dto.Author;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
    private int authorID;
    private String authorName;
    private String gender;
    private Date dateOfBirth;
}
