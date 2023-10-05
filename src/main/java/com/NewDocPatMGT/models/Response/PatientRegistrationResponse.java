package com.NewDocPatMGT.models.Response;


import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.models.Entity.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRegistrationResponse {

    private ApplicationUser user;
    private Patient patient;

    public PatientRegistrationResponse(ApplicationUser user, Patient patient) {
        this.user = user;
        this.patient = patient;
    }


}
