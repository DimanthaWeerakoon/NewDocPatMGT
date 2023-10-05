package com.NewDocPatMGT.models.DTO;

import com.NewDocPatMGT.models.Entity.Patient;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class AppointmentDTO {

    private Long id;
    private Date appointmentDate;
    private Time appointmentTime;
    private String appointmentMode;
    private Patient patient;
    private Long doctorId;

    public AppointmentDTO(Long doctorId, Date appointmentDate, Time appointmentTime, String appointmentMode) {
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentMode = appointmentMode;
    }
}
