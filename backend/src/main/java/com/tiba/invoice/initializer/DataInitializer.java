package com.tiba.invoice.initializer;

import com.tiba.invoice.entity.Role;
import com.tiba.invoice.entity.User;
import com.tiba.invoice.repository.RoleRepository;
import com.tiba.invoice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }

        if(userRepository.findByUserName("admin").isEmpty()){
            User user = new User();
            user.setUserName("admin");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setRoles(List.of(roleRepository.findByName("ROLE_ADMIN").get()));
            user.setEnabled(true);
            userRepository.save(user);
        }

    }
}
