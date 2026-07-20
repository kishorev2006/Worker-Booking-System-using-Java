package com.wbs.wbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbs.wbs.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
