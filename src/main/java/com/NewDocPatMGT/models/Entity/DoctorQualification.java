package com.NewDocPatMGT.models.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class DoctorQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String institute;
    private String completionYear;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public DoctorQualification(String degree, String institute, String completionYear) {
        this.degree = degree;
        this.institute = institute;
        this.completionYear = completionYear;
    }


//    public DoctorQualification() {
//
//    }
}
