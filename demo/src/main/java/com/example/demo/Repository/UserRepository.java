package com.example.demo.Repository;

import com.example.demo.Models.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserMail(String email);
}
