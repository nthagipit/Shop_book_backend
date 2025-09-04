package com.gipit.bookshop_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message="NAME_INVALID")
    private String firstName;
    @NotBlank(message="NAME_INVALID")
    private String lastName;

    @NotBlank(message="USERNAME_INVALID")
    @Size(min =6, message="USERNAME_INVALID")
    private String username;

    @NotBlank(message="PASSWORD_INVALID")
    @Size(min=8, message="PASSWORD_INVALID")
    private String password;

    @NotBlank(message="EMAIL_INVALID")
    @Email(message="EMAIL_INVALID")
    private String email;
}
