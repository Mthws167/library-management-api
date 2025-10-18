package com.library.library_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.library_management.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
     boolean existsByEmail(String email);
}
