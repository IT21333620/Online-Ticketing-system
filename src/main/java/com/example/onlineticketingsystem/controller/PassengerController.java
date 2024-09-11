package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.PassengerDTO;
import com.example.onlineticketingsystem.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/tickets")
@CrossOrigin
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    // Insert passenger
    @PostMapping("/createPassenger")
    public PassengerDTO savePassenger(@RequestBody PassengerDTO passengerDTO) {
        passengerService.savePassenger(passengerDTO);
        return passengerDTO; // Return a response indicating success
    }

    // Show all fields of passenger
    @GetMapping("/passengerById/{userID}")
    public PassengerDTO getPassengerById(@PathVariable int userID) {
        return passengerService.getPassengerById(userID);
    }

    // Show balance of passenger
    @GetMapping("/passengerBalance/{userID}")
    public int getPassengerByIdBalance(@PathVariable int userID) {
        return passengerService.getPassengerByIdBalance(userID);
    }

    // Update balance
    @PutMapping("/updateBalance/{amount}/{userID}")
    public ResponseEntity<Integer> updateBalanceByUserId(@PathVariable int amount, @PathVariable int userID) {
        passengerService.updateBalanceByUserId(amount, userID);
        int updatedBalance = passengerService.getBalanceByUserId(userID);
        return ResponseEntity.ok(updatedBalance);
    }

    // Check passenger credentials
    @GetMapping("/checkPassenger/{userID}/{password}")
    public boolean existsByUserIdAndPassword(@PathVariable int userID, @PathVariable String password) {
        return passengerService.existsByUserIdAndPassword(userID, password);
    }
}
