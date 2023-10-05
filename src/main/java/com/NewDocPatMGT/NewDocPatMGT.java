package com.NewDocPatMGT;

import java.util.HashSet;
import java.util.Set;

import com.NewDocPatMGT.models.DTO.RegistrationDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.NewDocPatMGT.models.Entity.ApplicationUser;
import com.NewDocPatMGT.models.Entity.Role;
import com.NewDocPatMGT.repository.RoleRepository;
import com.NewDocPatMGT.repository.UserRepository;

@SpringBootApplication
public class NewDocPatMGT {
	public static void main(String[] args) {
		SpringApplication.run(NewDocPatMGT.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("PATIENT"));
			roleRepository.save(new Role("DOCTOR"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin = new ApplicationUser(1, "admin", passwordEncode.encode("password"), roles);

			userRepository.save(admin);
		};
	}
}
