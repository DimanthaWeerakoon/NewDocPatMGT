package com.NewDocPatMGT.models.DTO;

import com.NewDocPatMGT.models.Entity.ApplicationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private ApplicationUser user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }
    public LoginResponseDTO(ApplicationUser user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }
}
