package com.NewDocPatMGT.models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO {

    private String position;
    private String specializedArea;
    private String doctorPhoto;
    private boolean status;
    private String language;
    private String qualifications;
    private String tasks;
    private double ratings;
}
