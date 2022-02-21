package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.AuthorizationRole;
import com.netcracker.game.data.model.enums.ERoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationRoleRepository extends JpaRepository<AuthorizationRole, Long> {

    Optional<AuthorizationRole> findByRoleName(ERoleType roleName);
}
