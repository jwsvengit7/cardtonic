package com.cardmonix.cardmonix.data_seeder;


import com.cardmonix.cardmonix.domain.constant.Role;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.UserRepository;
import com.cardmonix.cardmonix.utils.RandomValues;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;



@RequiredArgsConstructor
@Configuration
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User adminRole = new User();
        adminRole.setPassword(passwordEncoder.encode("12345"));
        adminRole.setRole(Role.ADMIN);

        adminRole.setActivate(true);
        adminRole.setEmail("app@cardmonix.com");
        adminRole.setUser_name("admin"+RandomValues.generateRandom());
        adminRole.setPhone("0909349345");
        userRepository.save(adminRole);


    }
}