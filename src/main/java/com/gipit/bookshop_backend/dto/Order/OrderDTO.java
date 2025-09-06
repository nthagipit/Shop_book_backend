package com.gipit.bookshop_backend.dto.Order;

import com.gipit.bookshop_backend.models.OrderDetails;
import com.gipit.bookshop_backend.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private int orderID;
    private String purchaseAddress;
    private String deliveryAddress;
    private String statusOrder;
    private Date deliveryDate;
//    private List<OrderDetails> listOrderDetails;


}
