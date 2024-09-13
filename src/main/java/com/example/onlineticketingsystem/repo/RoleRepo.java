package com.example.onlineticketingsystem.repo;

import com.example.onlineticketingsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findById(int id);
}
