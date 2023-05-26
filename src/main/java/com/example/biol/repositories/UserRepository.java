package com.example.biol.repositories;

import com.example.biol.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByActivationCode(String code);
    User findByUsername(String username);
}
