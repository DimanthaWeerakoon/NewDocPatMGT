package com.NewDocPatMGT.controllers;


import com.NewDocPatMGT.models.DTO.AppointmentDTO;
import com.NewDocPatMGT.models.DTO.RegistrationDTO;
import com.NewDocPatMGT.models.Entity.Appointment;
import com.NewDocPatMGT.models.Response.LoginResponse;
import com.NewDocPatMGT.models.Response.PatientRegistrationResponse;
import com.NewDocPatMGT.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public PatientRegistrationResponse registerUser(@RequestBody RegistrationDTO body) {
        return patientService.registerPatient(body.getUsername(), body.getPassword(), body.getEmail(), body.getFirstName(), body.getLastName(), body.getMobile(), body.getAge(), body.getGender(), body.getOrigin());
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody RegistrationDTO body) {
        return patientService.loginPatient(body.getUsername(), body.getPassword());
    }

    @PostMapping("/{patientId}/appointment-booking")
    public ResponseEntity<String> appointmentBooking(@PathVariable Long patientId, @RequestBody AppointmentDTO appointmentDTO) {
        try {
            Object result = patientService.appointmentBooking(patientId, appointmentDTO.getDoctorId(), appointmentDTO.getAppointmentDate(), appointmentDTO.getAppointmentTime(), appointmentDTO.getAppointmentMode(), appointmentDTO.getPatientSymptom());

            if (result instanceof String) {
                return ResponseEntity.ok(result.toString());
            } else if (result instanceof Appointment) {
                return ResponseEntity.ok("Appointment confirmed!");
            } else {
                return ResponseEntity.ok("Unknown response.");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("Appointment booking failed!");
        }
    }

}   
