package com.example.onlineticketingsystem.repo;


import com.example.onlineticketingsystem.entity.TicketInspector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketInspectorRepo extends JpaRepository<TicketInspector,Integer> {

    Optional<TicketInspector> findByEmail(String email);

    List<TicketInspector> findByInspectorID(int inspectorID);
}
