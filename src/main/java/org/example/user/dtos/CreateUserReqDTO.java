package org.example.user.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.user.models.Address;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateUserReqDTO {
    private String email;
    private String userName;
    private String password;
    private Name name;
    private List<Address> address;
    private String number;

    @Getter
    @Setter
    public static class Name{
        private String firstName;
        private String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
