package com.dama.backend.dama.repository;

import com.dama.backend.dama.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}