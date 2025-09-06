package com.gipit.bookshop_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gipit.bookshop_backend.dto.request.CreateUserRequest;
import com.gipit.bookshop_backend.dto.response.UserResponse;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.services.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Value("${api.prefix}")
    private String apiPrefix;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private CreateUserRequest createUserRequest;
    private UserResponse userResponse;
    private User mockUser;
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
    void creatUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content =objectMapper.writeValueAsString(createUserRequest);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix+"/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Create success"));

    }
    @Test
    void createUser_userExisted_fail() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content=objectMapper.writeValueAsString(createUserRequest);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenThrow(new AppException(ErrorCode.USER_EXISTS));

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix+"/users").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("User existed"));

    }
    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CreateUserRequest request=createUserRequest;
        request.setUsername("thoha");
        String content=objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix+"/users").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 6 characters"));
    }
    @Test
    void createUser_passwordInvalid_fail() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CreateUserRequest request=createUserRequest;
        request.setPassword("nguyen");
        String content =objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix+"/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Password must be at least 8 characters"));
    }
}
