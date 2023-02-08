package com.hyowon.note.user.repository;

import com.hyowon.note.user.entity.User;
import com.hyowon.note.user.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void getUser(){
        Optional<User> user = userRepository.findByEmail("skgydnjs@gmail.com");

        System.out.println(user.get().getEmail());

    }
}
