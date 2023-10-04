package com.NewDocPatMGT.models.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String mobile;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password, String email, String firstName, String lastName, String mobile) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
    }

    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password;
    }
}
