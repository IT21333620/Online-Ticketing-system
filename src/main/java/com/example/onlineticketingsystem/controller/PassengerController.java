package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.PassengerDTO;
import com.example.onlineticketingsystem.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/tickets")
@CrossOrigin
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    // Show all fields of passenger
    @GetMapping("/passengerById/{userID}")
    public ResponseEntity<?> getPassengerById(@PathVariable int userID, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger") || grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have passenger Or admin role.");
        }
        PassengerDTO passengerDTO = passengerService.getPassengerById(userID);
        return new ResponseEntity<>(passengerDTO, HttpStatus.OK);
    }

    // Show balance of passenger
    @GetMapping("/passengerBalance/{userID}")
    public ResponseEntity<?> getPassengerByIdBalance(@PathVariable int userID, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger") || grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have passenger Or admin role.");
        }
        int balance = passengerService.getBalanceByUserId(userID);
        return ResponseEntity.ok(balance);
    }

    // Update balance
    @PutMapping("/updateBalance/{amount}/{userID}")
    public ResponseEntity<?> updateBalanceByUserId(@PathVariable int amount, @PathVariable int userID, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger") || grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have passenger Or admin role.");
        }
        passengerService.updateBalanceByUserId(amount, userID);
        int updatedBalance = passengerService.getBalanceByUserId(userID);
        return ResponseEntity.ok(updatedBalance);
    }
}
