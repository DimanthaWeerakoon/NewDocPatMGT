package com.NewDocPatMGT.services;

import com.NewDocPatMGT.models.DTO.LoginResponseDTO;
import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.models.Entity.Doctor;
import com.NewDocPatMGT.models.Entity.Role;
import com.NewDocPatMGT.models.Response.DoctorRegistrationResponse;
import com.NewDocPatMGT.repository.DoctorRepository;
import com.NewDocPatMGT.repository.RoleRepository;
import com.NewDocPatMGT.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public DoctorService(UserRepository userRepository, DoctorRepository doctorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public DoctorRegistrationResponse registerDoctor(String username, String password, String email, String firstName, String lastName, String mobile, String position, String specializedArea, String language, String qualifications) {

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("DOCTOR").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);
        ApplicationUser user = userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities, email, firstName, lastName, mobile));
        Doctor doctor = doctorRepository.save(new Doctor(position, specializedArea, language, qualifications));

        DoctorRegistrationResponse response = new DoctorRegistrationResponse(user, doctor);
        response.setUser(user);
        response.setDoctor(doctor);

        return response;
    }

    public LoginResponseDTO loginDoctor(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
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

}
