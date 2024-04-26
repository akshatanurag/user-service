package org.example.user.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.user.models.Address;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CreateUserResDTO {
    private RequestStatus status;
    private Long id;
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
        public Name(){}
        public Name(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
