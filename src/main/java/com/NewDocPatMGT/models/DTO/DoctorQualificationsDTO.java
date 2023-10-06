package com.NewDocPatMGT.models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorQualificationsDTO {

    private String degree;
    private String institute;
    private String completionYear;


    public DoctorQualificationsDTO(String degree, String institute, String completionYear) {
        this.degree = degree;
        this.institute = institute;
        this.completionYear = completionYear;
    }
}
