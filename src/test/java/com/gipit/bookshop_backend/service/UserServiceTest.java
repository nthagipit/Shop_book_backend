package com.gipit.bookshop_backend.service;

import com.gipit.bookshop_backend.dto.request.CreateUserRequest;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.repositories.UserRepository;
import com.gipit.bookshop_backend.services.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private CreateUserRequest createUserRequest;
    private User mockUser ;

    @BeforeEach
    void initData(){
        createUserRequest = CreateUserRequest.builder()
                .lastName("Tho")
                .firstName("Ha")
                .username("thohagipit1")
                .password("nguyenthoha20041")
                .email("thoha2008200412@gmail.com")
                .build();
        mockUser= User.builder()
                .username("thohagipit1")
                .email("thoha2008200412@gmail.com")
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(null);
        Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(mockUser);

        var response= userService.createUser(createUserRequest);

        Assertions.assertEquals(response.getUsername(),"thohagipit1");
        Assertions.assertEquals(response.getEmail(),"thoha2008200412@gmail.com");
    }
    @Test
    void createUser_userExisted_fail(){
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(mockUser);

        var exception= Assertions.assertThrows(AppException.class,()->{userService.createUser(createUserRequest);});

        Assertions.assertEquals(exception.getErrorCode().getCode(),1001);
    }
    @Test
    void createUser_emailExisted_fail(){
        Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(true);
        var exception= Assertions.assertThrows(AppException.class,()->{userService.createUser(createUserRequest);});
        Assertions.assertEquals(exception.getErrorCode().getCode(),1002);
    }
}
