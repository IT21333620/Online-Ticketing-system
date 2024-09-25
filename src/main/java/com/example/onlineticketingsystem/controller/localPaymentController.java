package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.LocalPaymentDTO;
import com.example.onlineticketingsystem.entity.LocalPayment;
import com.example.onlineticketingsystem.service.LocalPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/tickets")
@CrossOrigin
public class localPaymentController {
    @Autowired
    private LocalPaymentService localPaymentService;

    @PostMapping("/saveLocalPayment")
    public ResponseEntity<?> saveUser(@RequestBody LocalPaymentDTO localPaymentDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger")) || authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(401).body("Access denied. You need to have passenger or admin role.");
        }
        LocalPaymentDTO localPayment = localPaymentService.saveLocalPayment(localPaymentDTO);
        return ResponseEntity.ok(localPayment);
    }

    @GetMapping("/getLocalPayment/{userID}")
    public ResponseEntity<?> getLocalPaymentByUserId(@PathVariable int userID, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger")) || authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have passenger or admin role.");
        }
        List<LocalPayment> localPayment = localPaymentService.getLocalPaymentByUserId(userID);
        return ResponseEntity.ok(localPayment);
    }

    @GetMapping("/getLocalUniquePayment/{userID}/{refNumber}")
    public ResponseEntity<?> getLocalPaymentByUserIdAndRef(@PathVariable int userID,@PathVariable String refNumber, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger")) || authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have passenger or admin role.");
        }
        List<LocalPayment> localPayment = localPaymentService.getLocalPaymentByUserIdAndRef(userID,refNumber);
        return ResponseEntity.ok(localPayment);
    }
}
