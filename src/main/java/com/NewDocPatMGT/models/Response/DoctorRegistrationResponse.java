package com.NewDocPatMGT.models.Response;

import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.models.Entity.Doctor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRegistrationResponse {

    private ApplicationUser user;
    private Doctor doctor;

    public DoctorRegistrationResponse(ApplicationUser user, Doctor doctor) {
        this.user = user;
        this.doctor = doctor;
    }
}
