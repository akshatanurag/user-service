package org.example.user.controllers;

import org.example.user.dtos.CreateUserReqDTO;
import org.example.user.dtos.CreateUserResDTO;
import org.example.user.exceptions.FieldNotFoundException;
import org.example.user.models.Address;
import org.example.user.models.User;
import org.example.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserController userController;

    @MockBean
    UserService userService;

    @Test
    void getAllUsers() {
    }

    @Test
    void getSingleUser() {
    }

    @Test
    void addUser() throws FieldNotFoundException {
        CreateUserReqDTO user = CreateUserReqDTO
                .builder()
                .userName("akshat@gmail.com")
                .email("akshat@gmail.com")
                .address(List.of(new Address()))
                .name(new CreateUserReqDTO.Name("Akshat","Anurag"))
                .number("9051799755")
                .build();

        CreateUserResDTO savedUser = CreateUserResDTO
                .builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .address(user.getAddress())
                .name(new CreateUserResDTO.Name(user.getName().getFirstName(),user.getName().getLastName()))
                .number(user.getNumber())
                .id(1L)
                .build();

        when(
                userService.addUser(user)
        ).thenReturn(
                savedUser
        );

        ResponseEntity<CreateUserResDTO> createUserResDTO = userController.addUser(user);

        assertEquals(savedUser,createUserResDTO.getBody());

        verify(userService, times(1)).addUser(user);
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}