package com.gipit.bookshop_backend.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCartDetailsRequest {
    private int userID;
    private int bookID;
}
