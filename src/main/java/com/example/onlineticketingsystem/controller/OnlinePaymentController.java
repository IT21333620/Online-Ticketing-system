package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.LocalPaymentDTO;
import com.example.onlineticketingsystem.DTO.OnlinePaymentDTO;
import com.example.onlineticketingsystem.entity.LocalPayment;
import com.example.onlineticketingsystem.entity.OnlinePayment;
import com.example.onlineticketingsystem.service.LocalPaymentService;
import com.example.onlineticketingsystem.service.OnlinePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/tickets")
@CrossOrigin
public class OnlinePaymentController {
    @Autowired
    private OnlinePaymentService onlinePaymentService;

    @PostMapping("/saveOnlinePayment")
    public ResponseEntity<?> saveUser(@RequestBody OnlinePaymentDTO onlinePaymentDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have passenger role.");
        }
        OnlinePaymentDTO onlinePayment = onlinePaymentService.saveOnlinePayment(onlinePaymentDTO);
        return ResponseEntity.ok(onlinePayment);
    }

    @GetMapping("/getOnlinePayment/{userID}")
    public ResponseEntity<?> getOnlinePaymentByUserId(@PathVariable int userID, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("passenger"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have passenger role.");
        }
        List<OnlinePayment> onlinePayment = onlinePaymentService.getOnlinePaymentByUserId(userID);
        return ResponseEntity.ok(onlinePayment);
    }
}
