package com.NewDocPatMGT.controllers;

import com.NewDocPatMGT.defaultResponse.DefaultResponse;
import com.NewDocPatMGT.models.DTO.DoctorDTO;
import com.NewDocPatMGT.models.DTO.LoginResponseDTO;
import com.NewDocPatMGT.models.DTO.RegistrationDTO;
import com.NewDocPatMGT.models.Response.DoctorRegistrationResponse;
import com.NewDocPatMGT.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public DoctorRegistrationResponse registerUser(@RequestBody RegistrationDTO body) {
        return doctorService.registerDoctor(body.getUsername(), body.getPassword(), body.getEmail(), body.getFirstName(), body.getLastName(), body.getMobile(), body.getPosition(), body.getSpecializedArea(), body.getLanguage(), body.getQualifications());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return doctorService.loginDoctor(body.getUsername(), body.getPassword());
    }

    @PutMapping("/{doctorId}/availability")
    public ResponseEntity<String> setDoctorAvailability(@PathVariable Long doctorId, @RequestBody Map<String, Boolean> requestBody) {
        Boolean available = requestBody.get("available");
        try{
            doctorService.setDoctorAvailability(doctorId, available);
            return ResponseEntity.ok("Availability status updated successfully.");
        }catch (Exception e){
            return ResponseEntity.ok("Availability status update failed.");
        }
    }

    @PutMapping("/{doctorId}/preference")
    public ResponseEntity<String> setDoctorPreferences(@PathVariable Long doctorId, @RequestBody DoctorDTO doctorDTO){
        try{
            doctorService.setDoctorPreferences(doctorId, doctorDTO.getLanguage(), doctorDTO.getMedium());
            return ResponseEntity.ok("Preferences changed successfully.");
        }catch (Exception e){
            return ResponseEntity.ok("Failed to change preferences");
        }
    }
}
