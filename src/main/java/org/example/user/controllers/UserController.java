package org.example.user.controllers;

import org.example.user.dtos.*;
import org.example.user.exceptions.FieldNotFoundException;
import org.example.user.exceptions.ResourceNotFoundException;
import org.example.user.exceptions.UserNotFoundException;
import org.example.user.models.Token;
import org.example.user.models.User;
import org.example.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws UserNotFoundException {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        Token token = userService.login(email,password);
        AuthResDTO authResDTO = AuthResDTO.from(token.getUser());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token.getValue());

        return new ResponseEntity<>(authResDTO, headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public AuthResDTO signup(@RequestBody SignUpReqDTO signUpReqDTO) throws FieldNotFoundException {
        String email = signUpReqDTO.getEmail();
        String name = signUpReqDTO.getName();
        String password = signUpReqDTO.getPassword();
        return AuthResDTO.from(userService.signup(name,email,password));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader Map<String,String> req) throws ResourceNotFoundException {
        userService.logout(req.get("token"));
        return new ResponseEntity<>("Logged Out",HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestHeader("Authorization") String token){
        Boolean isValid = userService.validateToken(token);
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", isValid);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    /* Admin APIs */

    @GetMapping()
    public List<User> getAllUsers() throws UserNotFoundException {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserResDTO> getSingleUser(@PathVariable(name = "id")Long id) throws UserNotFoundException {
        CreateUserResDTO user = userService.getSingleUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreateUserResDTO> addUser(@RequestBody CreateUserReqDTO createUserReqDTO) throws FieldNotFoundException {
            CreateUserResDTO user = userService.addUser(createUserReqDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(name = "id")Long id,@RequestBody CreateUserReqDTO createUserReqDTO) throws UserNotFoundException {
        return userService.updateUser(id,createUserReqDTO);
    }

    @DeleteMapping("/{id}")
    public CreateUserResDTO deleteUser(@PathVariable(name = "id")Long id){
        CreateUserResDTO createUserResDTO;
        try {
            userService.deleteUser(id);
            createUserResDTO=CreateUserResDTO.builder().status(RequestStatus.SUCCESS).build();
        } catch (Exception e){
            createUserResDTO=CreateUserResDTO.builder().status(RequestStatus.FAILURE).build();
        }

        return createUserResDTO;

    }
}
