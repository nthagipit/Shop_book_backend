package com.gipit.bookshop_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gipit.bookshop_backend.dto.request.CreateUserRequest;
import com.gipit.bookshop_backend.dto.response.UserResponse;
import com.gipit.bookshop_backend.models.Role;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.repositories.RoleRepository;
import com.gipit.bookshop_backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {

    @Container
    static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.36");
    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driver-class-name",()->"com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto",()->"update");
    }

    @Value("${api.prefix}")
    private String apiPrefix;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    private CreateUserRequest createUserRequest;
    private UserResponse userResponse;
    private User mockUser;
    @BeforeEach
    void initData(){
        if (roleRepository.findByRoleName("USER").isEmpty()) {
            roleRepository.save(Role.builder().roleName("USER").build());
        }
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
    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }
    @Test
    void creatUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content =objectMapper.writeValueAsString(createUserRequest);


        var response=mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix+"/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Create success"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.username").value("thohagipit1"));
            log.info("Result:{}",response.andReturn().getResponse().getContentAsString());

    }
    @Test
    void createUser_userExisted_fail() throws Exception {
        if (roleRepository.findByRoleName("USER").isEmpty()) {
            roleRepository.save(Role.builder().roleName("USER").build());
        }
        User userExisted= User.builder()
                .username("thohagipit1")
                .email("thoha2008200412@gmail.com")
                .lastName("Tho")
                .firstName("Ha")
                .password("nguyenthoha20041")
                .build();
        userRepository.save(userExisted);
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content=objectMapper.writeValueAsString(createUserRequest);

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
