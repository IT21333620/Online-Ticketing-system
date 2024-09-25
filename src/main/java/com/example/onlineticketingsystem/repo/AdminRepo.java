package com.example.onlineticketingsystem.repo;

import com.example.onlineticketingsystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin,Integer> {

    Optional<Admin> findByEmail(String email);
}
