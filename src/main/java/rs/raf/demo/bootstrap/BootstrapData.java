package rs.raf.demo.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

//        System.out.println("Loading Data...");
//
//        User user1 = new User();
//        user1.setEmail("user1");
//        user1.setPassword(this.passwordEncoder.encode("user1"));
//        this.userRepository.save(user1);
//
//        User user2 = new User();
//        user2.setEmail("user2");
//        user2.setPassword(this.passwordEncoder.encode("user2"));
//        this.userRepository.save(user2);
//
//        User user3 = new User();
//        user3.setEmail("user3");
//        user3.setPassword(this.passwordEncoder.encode("user3"));
//        this.userRepository.save(user3);

//        System.out.println("Data loaded!");
    }
}
