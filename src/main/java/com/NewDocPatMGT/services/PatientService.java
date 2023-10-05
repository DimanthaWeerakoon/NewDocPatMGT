package com.NewDocPatMGT.services;

import com.NewDocPatMGT.models.Entity.*;
import com.NewDocPatMGT.models.Response.LoginResponse;
import com.NewDocPatMGT.models.Response.PatientRegistrationResponse;
import com.NewDocPatMGT.repository.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public PatientService(UserRepository userRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public PatientRegistrationResponse registerPatient(String username, String password, String email, String firstName, String lastName, String mobile, String age, String gender, String origin) {

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("PATIENT").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);
        ApplicationUser user = userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities, email, firstName, lastName, mobile));
        Patient patient = patientRepository.save(new Patient(age, gender, origin));

        PatientRegistrationResponse response = new PatientRegistrationResponse(user, patient);
        response.setUser(user);
        response.setPatient(patient);
        return response;
    }

    public LoginResponse loginPatient(String username, String password) {

        try {

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateJwt(auth);
            return new LoginResponse(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponse(null, "");
        }
    }

    public Object appointmentBooking(Long patientId, Long doctorId, Date appointmentDate, Time appointmentTime, String appointmentMode) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if (patient != null && doctor != null) {
            if (doctor.isAvailable()) {
                try {
                    return appointmentRepository.save(new Appointment(patient, doctor, appointmentDate, appointmentTime, appointmentMode));
                } catch (Exception e) {
                    return "Failed to create appointment.";
                }
            } else {
                return "Doctor is not available!";
            }
        }
        return "Patient or Doctor not found.";
    }


}
