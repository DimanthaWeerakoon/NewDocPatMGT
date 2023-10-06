package com.NewDocPatMGT.models.DTO;

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
    private String age;
    private String gender;
    private String origin;
    private String position;
    private String specializedArea;
    private String language;

    public RegistrationDTO() {
        super();
    }

    public String toString() {
        return "Registration info: username: " + this.username + " password: " + this.password;
    }
}
