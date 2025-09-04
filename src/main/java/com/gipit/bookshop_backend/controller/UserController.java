package com.gipit.bookshop_backend.controller;

import com.gipit.bookshop_backend.dto.request.CreateUserRequest;
import com.gipit.bookshop_backend.dto.request.UpdateUserRequest;
import com.gipit.bookshop_backend.dto.response.ApiResponse;
import com.gipit.bookshop_backend.dto.response.UserResponse;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.services.impl.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User user=userService.createUser(createUserRequest);
        return ApiResponse.<UserResponse>builder()
                .message("Create success")
                .data(modelMapper.map(user, UserResponse.class))
                .build();
    }
    @PostMapping("/update/{userID}")
    public ApiResponse<UserResponse> updateInformationUser(@PathVariable int userID,@RequestBody @Valid UpdateUserRequest updateUserRequest){
            User user=userService.updateUser(userID,updateUserRequest);
            return ApiResponse.<UserResponse>builder()
                    .message("Update sucess")
                    .data(modelMapper.map(user, UserResponse.class))
                    .build();
    }
    @GetMapping("/active/{activationCode}")
    public ApiResponse<Boolean> activateUser(@PathVariable String activationCode) {
        String message="Activate failed";
        boolean rs=userService.activeAccount(activationCode);
        if(rs){
            message="Activate success";
        }
        return ApiResponse.<Boolean>builder()
                .message(message)
                .data( rs)
                .build();

    }
}
