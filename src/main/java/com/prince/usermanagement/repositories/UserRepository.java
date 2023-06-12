package com.prince.usermanagement.repositories;

import com.prince.usermanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByDeletedFalse(); // Retrieve non-deleted users
    public Optional<User> findByIdAndDeletedFalse(Long id); // Retrieve non-deleted user by ID
    Optional<User> findByUsername(String username); // Retrieve user by Username
    Optional<User> findByEmail(String email); // Retrieve user by Email
}
