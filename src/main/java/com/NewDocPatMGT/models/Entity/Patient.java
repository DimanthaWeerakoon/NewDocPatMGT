package com.NewDocPatMGT.models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String gender;
    private String age;
    private String origin;
    private String userPhoto;
    private String records;

    @OneToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;


}