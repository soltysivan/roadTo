package org.euro.service;


import org.euro.dao.entity.Role;
import org.euro.dao.entity.User;
import org.euro.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CommandLineAppStartupRunner  implements CommandLineRunner {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User user = userRepository.findByUsername("admin");
        if (user == null){
            User admin = new User();
            admin.setActivationCode(null);
            admin.setActive(true);
            admin.setAvatar(null);
            admin.setRoles(Collections.singleton(Role.USER));
            admin.setEmail("vasia@mail.com");
            admin.setFirstName("Vasyl");
            admin.setLastName("Lavriv");
            admin.setNewMes(0);
            admin.setPassword(passwordEncoder.encode("1111"));
            admin.setTelephone("+380");
            admin.setUsername("admin");
            userRepository.save(admin);
            User user1 = userRepository.findByUsername("admin");
            user1.getRoles().add(Role.ADMIN);
            userRepository.save(user1);
        }

    }
}
