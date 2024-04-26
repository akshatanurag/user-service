package org.example.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
public class Token extends BaseModel{
    @Column(length = 2000)
    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;

    public Token() {

    }
}
