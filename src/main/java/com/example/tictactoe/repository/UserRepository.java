package com.example.tictactoe.repository;

import com.example.tictactoe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    // Optional is because, if there is no user, then we allow this function ot give null, instead of null pointer exception.

    // This does: Select count(*) > 0, where email = email
    boolean existsByEmail(String email);
}
