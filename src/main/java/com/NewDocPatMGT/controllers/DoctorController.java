package com.NewDocPatMGT.controllers;

import com.NewDocPatMGT.models.DTO.LoginResponseDTO;
import com.NewDocPatMGT.models.DTO.RegistrationDTO;
import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
@CrossOrigin("*")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body) {
        return doctorService.registerDoctor(body.getUsername(), body.getPassword(), body.getEmail(), body.getFirstName(), body.getLastName(), body.getMobile());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return doctorService.loginDoctor(body.getUsername(), body.getPassword());
    }
}
