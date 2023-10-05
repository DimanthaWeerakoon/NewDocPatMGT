package com.NewDocPatMGT.models.Response;

import com.NewDocPatMGT.models.Entity.ApplicationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private ApplicationUser user;
    private String jwt;

    public LoginResponse(){
        super();
    }
    public LoginResponse(ApplicationUser user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }
}
