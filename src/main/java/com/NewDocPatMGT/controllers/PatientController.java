package com.NewDocPatMGT.controllers;



import com.NewDocPatMGT.models.DTO.LoginResponseDTO;
import com.NewDocPatMGT.models.DTO.RegistrationDTO;
import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body) {
        return patientService.registerPatient(body.getUsername(), body.getPassword(), body.getEmail(), body.getFirstName(), body.getLastName(), body.getMobile());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return patientService.loginPatient(body.getUsername(), body.getPassword());
    }
}   
