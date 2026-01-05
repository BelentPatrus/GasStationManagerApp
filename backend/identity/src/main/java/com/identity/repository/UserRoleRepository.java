package com.identity.repository;

import com.identity.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.Pk> {
    List<UserRole> findAllByUserId(UUID userId);
}
