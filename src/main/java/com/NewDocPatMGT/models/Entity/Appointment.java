package com.NewDocPatMGT.models.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date appointmentDate;
    private Time appointmentTime;
    private String appointmentMode;
    private String patientSymptom;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Appointment(Patient patient, Doctor doctor, Date appointmentDate, Time appointmentTime, String appointmentMode, String patientSymptom) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentMode = appointmentMode;
        this.patientSymptom =    patientSymptom;
    }

    public Appointment() {
    }
}
