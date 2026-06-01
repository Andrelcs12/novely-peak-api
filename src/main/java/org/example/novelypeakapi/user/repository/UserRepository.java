package org.example.novelypeakapi.user.repository;

import org.example.novelypeakapi.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Só de estender o JpaRepository, o Spring já nos dá o método .findById(id) de graça!
}