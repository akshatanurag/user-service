package org.example.user.services;

import org.example.user.dtos.AuthResDTO;
import org.example.user.dtos.CreateUserReqDTO;
import org.example.user.dtos.CreateUserResDTO;
import org.example.user.dtos.RequestStatus;
import org.example.user.exceptions.FieldNotFoundException;
import org.example.user.exceptions.ResourceNotFoundException;
import org.example.user.exceptions.UserNotFoundException;
import org.example.user.models.Address;
import org.example.user.models.Token;
import org.example.user.models.User;
import org.example.user.repositories.TokenRepository;
import org.example.user.repositories.UserRepository;
import org.example.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    UserServiceImpl(UserRepository userRepository,
                    TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder,JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public List<User> getAllUser() throws UserNotFoundException {
        Optional<List<User>> userListOptional = userRepository.getAllUsers();
        if(userListOptional.isEmpty()){
            throw new UserNotFoundException("No User Present In The DB");
        }

        return userListOptional.get();
    }

    @Override
    public CreateUserResDTO getSingleUser(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            throw new UserNotFoundException("User With The ID " + id + " Is Not Present.");

        User user = userOptional.get();
        return CreateUserResDTO.builder()
                .userName(user.getUserName())
                .name(new CreateUserResDTO.Name(user.getFirstName(),user.getLastName()))
                .email(user.getEmail())
                .address(user.getAddress())
                .number(user.getNumber())
                .userName(user.getUserName())
                .status(RequestStatus.SUCCESS)
                .id(user.getId())
                .build();
    }

    @Override
    public CreateUserResDTO addUser(CreateUserReqDTO createUserReqDTO) throws FieldNotFoundException {

        Address addressFromReq = createUserReqDTO.getAddress().get(0);
        Optional<Address> address = userRepository.getAddressOfUser(addressFromReq.getCity(),addressFromReq.getStreet(),addressFromReq.getZipcode());
        if(!address.isEmpty()){
            createUserReqDTO.setAddress(List.of(address.get()));
        }

        User user = User.builder()
                .userName(createUserReqDTO.getUserName())
                .email(createUserReqDTO.getEmail())
                .firstName(createUserReqDTO.getName().getFirstName())
                .lastName(createUserReqDTO.getName().getLastName())
                .address(createUserReqDTO.getAddress())
                .number(createUserReqDTO.getNumber())
                .password(createUserReqDTO.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return CreateUserResDTO.builder()
                .userName(savedUser.getUserName())
                .name(new CreateUserResDTO.Name(savedUser.getFirstName(),savedUser.getLastName()))
                .email(savedUser.getEmail())
                .address(savedUser.getAddress())
                .number(savedUser.getNumber())
                .userName(savedUser.getUserName())
                .status(RequestStatus.SUCCESS)
                .id(savedUser.getId())
                .build();
    }

    @Override
    public User updateUser(Long id, CreateUserReqDTO createUserReqDTO) throws UserNotFoundException {
        Optional<User> savedUser = userRepository.findById(id);
        if(savedUser.isEmpty())
            throw new UserNotFoundException("User Not Found For Update");

        User user = createUserObjFromDTO(createUserReqDTO,savedUser.get());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Token login(String email,String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty())
            throw new UserNotFoundException("User Not Found");
        User user = userOptional.get();

        if(!passwordEncoder.matches(password,user.getPassword()))
            throw new UserNotFoundException("Invalid Password");

        LocalDate localDate = LocalDate.now().plusDays(30);
        Date expiresAt = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String jwtToken = jwtUtil.generateToken(AuthResDTO.from(user));
        Token token = Token.builder().user(user).expiryAt(expiresAt).value(jwtToken).build();
        return tokenRepository.save(token);

    }

    @Override
    public void logout(String token) throws ResourceNotFoundException {
        Optional<Token> tokenOptional = tokenRepository.findTokenByValue(token);
        if(tokenOptional.isEmpty())
            throw new ResourceNotFoundException("Invalid Token");
        tokenRepository.deleteTokenById(tokenOptional.get().getId());
    }

    @Override
    public User signup(String name, String email, String password) throws FieldNotFoundException {
        List<String> nameSplit = List.of(name.split(" "));
        String hashedPassword = passwordEncoder.encode(password);
        User user = User.builder().firstName(nameSplit.get(0)).lastName(nameSplit.get(1)).email(email).password(hashedPassword).build();
        return userRepository.save(user);
    }

    @Override
    public Boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    private User createUserObjFromDTO(CreateUserReqDTO createUserReqDTO,User user){
        user.setEmail(createUserReqDTO.getEmail());
        user.setPassword(createUserReqDTO.getPassword());
        user.setUserName(createUserReqDTO.getUserName());
        user.setFirstName(createUserReqDTO.getName().getFirstName());
        user.setLastName(createUserReqDTO.getName().getLastName());
        user.setAddress(createUserReqDTO.getAddress());
        user.setNumber(createUserReqDTO.getNumber());
        return user;
    }
}
