package com.example.onlineticketingsystem.repo;

import com.example.onlineticketingsystem.entity.BusOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusOwnerRepo extends JpaRepository<BusOwner, Integer> {

    Optional<BusOwner> findByEmail(String email);
}
