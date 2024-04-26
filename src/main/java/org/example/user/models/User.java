package org.example.user.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.user.exceptions.FieldNotFoundException;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class User extends BaseModel{

    private String email;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Address> address;
    private String number;
    @ManyToMany
    private List<Role> role;

    public User(){}

    public User(UserBuilder userBuilder) {
        this.email  = userBuilder.email;
        this.userName = userBuilder.userName;
        this.password = userBuilder.password;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.address = userBuilder.address;
        this.number = userBuilder.number;
    }

//    public static class UserBuilder{
//        public User build() throws FieldNotFoundException {
//            if(this.email==null || this.userName ==null)
//                throw new FieldNotFoundException("Email OR Username are required");
//
//            return new User(this);
//        }
//    }
}
