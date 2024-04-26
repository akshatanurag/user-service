package org.example.user.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.user.models.Address;
import org.example.user.models.Role;
import org.example.user.models.User;

import java.util.List;

@Getter
@Setter
public class AuthResDTO {
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private List<Address> address;
    private String number;
    private List<Role> role;

    public AuthResDTO() {
    }

    public static AuthResDTO from(User user) {
        AuthResDTO authResDTO = new AuthResDTO();
        authResDTO.email = user.getEmail();
        authResDTO.userName = user.getUserName();
        authResDTO.firstName = user.getFirstName();
        authResDTO.lastName = user.getLastName();
        authResDTO.address = user.getAddress();
        authResDTO.number = user.getNumber();
        authResDTO.role = user.getRole();

        return authResDTO;
    }
}
