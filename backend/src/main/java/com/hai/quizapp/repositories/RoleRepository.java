package com.hai.quizapp.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hai.quizapp.entities.Role;
import com.hai.quizapp.enums.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleEnum name);

    boolean existsByName(RoleEnum name);
}
