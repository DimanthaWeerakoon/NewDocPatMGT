package com.NewDocPatMGT.services;

import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.models.Entity.Doctor;
import com.NewDocPatMGT.models.Entity.DoctorQualification;
import com.NewDocPatMGT.models.Entity.Role;
import com.NewDocPatMGT.models.Response.DoctorRegistrationResponse;
import com.NewDocPatMGT.models.Response.LoginResponse;
import com.NewDocPatMGT.repository.DoctorRepository;
import com.NewDocPatMGT.repository.QualificationRepository;
import com.NewDocPatMGT.repository.RoleRepository;
import com.NewDocPatMGT.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final QualificationRepository qualificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public DoctorService(UserRepository userRepository, DoctorRepository doctorRepository, RoleRepository roleRepository, QualificationRepository qualificationRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
        this.qualificationRepository = qualificationRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public DoctorRegistrationResponse registerDoctor(String username, String password, String email, String firstName, String lastName, String mobile, String position, String specializedArea, String language) {

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("DOCTOR").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);
        ApplicationUser user = userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities, email, firstName, lastName, mobile));
        Doctor doctor = doctorRepository.save(new Doctor(position, specializedArea, language));

        DoctorRegistrationResponse response = new DoctorRegistrationResponse(user, doctor);
        response.setUser(user);
        response.setDoctor(doctor);

        return response;
    }

    public LoginResponse loginDoctor(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateJwt(auth);
            return new LoginResponse(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponse(null, "");
        }
    }

    public void setDoctorAvailability(Long doctorId, boolean available) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setAvailable(available);
            doctorRepository.save(doctor);
        } else {
            throw new UsernameNotFoundException("Doctor with ID " + doctorId + " not found.");
        }
    }

    public void setDoctorPreferences(Long doctorId, String language, String medium) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setLanguage(language);
            doctor.setMedium(medium);
            doctorRepository.save(doctor);
        } else {
            throw new UsernameNotFoundException("Doctor with ID " + doctorId + " not found.");
        }
    }

    public Object setDoctorQualifications(Long doctorId, String degree, String institute, String completionYear) {

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null){
            DoctorQualification qualification = new DoctorQualification();
            qualification.setDegree(degree);
            qualification.setInstitute(institute);
            qualification.setCompletionYear(completionYear);
            qualificationRepository.save(qualification);
            return qualification;
        }else {
            return "Doctor not found";
        }
    }

    public Object editDoctorQualifications(Long doctorId, Long qualificationId, String degree, String institute, String completionYear) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            DoctorQualification qualification = qualificationRepository.findById(qualificationId).orElse(null);
            if (qualification != null) {
                // Update the qualification properties
                qualification.setDegree(degree);
                qualification.setInstitute(institute);
                qualification.setCompletionYear(completionYear);
                // Save the updated qualification
                qualificationRepository.save(qualification);
                return qualification;
            } else {
                return "Qualification not found";
            }
        } else {
            return "Doctor not found";
        }
    }




}
