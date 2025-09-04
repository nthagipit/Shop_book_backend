package com.gipit.bookshop_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailsRequest {
    private int cartDetailsID;
    private int quantity;
    private boolean ischecked;
}
