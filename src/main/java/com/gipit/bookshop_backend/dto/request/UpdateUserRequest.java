package com.gipit.bookshop_backend.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UpdateUserRequest {
    @Size(min=10 , max = 11, message="PHONENUMBER_INVALID")
    String phone;
    String gender;
    String deliveryAddress;
    String purchaseAddress;
}
