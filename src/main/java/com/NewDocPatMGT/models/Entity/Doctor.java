package com.NewDocPatMGT.models.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Doctor {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String position;
    private String specializedArea;
    private String doctorPhoto;
    private boolean available;
    private String language;
    private String medium;
    private String qualifications;
    private String tasks;
    private double ratings;

    @OneToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor(String position, String specializedArea, String language, String qualifications) {
        this.position = position;
        this.specializedArea = specializedArea;
        this.language = language;
        this.qualifications = qualifications;
    }

    public Doctor(String language, String medium) {
        this.language = language;
        this.medium = medium;
    }
}
