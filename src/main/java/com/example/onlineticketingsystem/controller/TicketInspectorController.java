package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.TicketInspectorDTO;
import com.example.onlineticketingsystem.entity.TicketInspector;
import com.example.onlineticketingsystem.service.TicketInspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/Inspector")
@CrossOrigin
public class TicketInspectorController {

    @Autowired
    private TicketInspectorService ticketInspectorService;


    @GetMapping("/getInspector")
    public ResponseEntity<?> getAllInspectors(Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        List<TicketInspectorDTO> ticketInspector = ticketInspectorService.getAllInspectors();
        return ResponseEntity.ok(ticketInspector);
    }

    @GetMapping("/byInspector/{id}")
    public ResponseEntity<?> getInspectorById(@PathVariable int id, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("ticket-inspector"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or ticket-inspector role.");
        }
        List<TicketInspector> ticketInspector = ticketInspectorService.getInspectorById(id);
        return ResponseEntity.ok(ticketInspector);
    }

}
