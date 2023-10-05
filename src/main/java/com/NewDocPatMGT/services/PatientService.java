package com.NewDocPatMGT.services;

import com.NewDocPatMGT.models.DTO.LoginResponseDTO;
import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.models.Entity.Patient;
import com.NewDocPatMGT.models.Entity.Role;
import com.NewDocPatMGT.models.Response.PatientRegistrationResponse;
import com.NewDocPatMGT.repository.PatientRepository;
import com.NewDocPatMGT.repository.RoleRepository;
import com.NewDocPatMGT.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public PatientService(UserRepository userRepository, PatientRepository patientRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
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

    public LoginResponseDTO loginPatient(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }


}
