package org.example.user.services;

import org.example.user.dtos.CreateUserReqDTO;
import org.example.user.dtos.CreateUserResDTO;
import org.example.user.exceptions.FieldNotFoundException;
import org.example.user.exceptions.ResourceNotFoundException;
import org.example.user.exceptions.UserNotFoundException;
import org.example.user.models.Token;
import org.example.user.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser() throws UserNotFoundException;

    CreateUserResDTO getSingleUser(Long id) throws UserNotFoundException;

    CreateUserResDTO addUser(CreateUserReqDTO createUserReqDTO) throws FieldNotFoundException;

    User updateUser(Long id, CreateUserReqDTO createUserReqDTO) throws UserNotFoundException;

    void deleteUser(Long id);

    Token login(String email, String password) throws UserNotFoundException;

    void logout(String token) throws ResourceNotFoundException;

    User signup(String name, String email, String password) throws FieldNotFoundException;

    Boolean validateToken(String token);
}
