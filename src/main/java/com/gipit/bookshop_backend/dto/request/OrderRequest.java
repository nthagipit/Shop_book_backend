package com.gipit.bookshop_backend.dto.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String purchaseAddress;
    private String deliveryAddress;
    private int procced_delivery;


}
