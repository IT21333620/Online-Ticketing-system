package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.BusDTO;
import com.example.onlineticketingsystem.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/bus")
@CrossOrigin
public class BusController {

    @Autowired
    private BusService busService;

//    @GetMapping
//    public ResponseEntity<?> helloBus(){
//        return ResponseEntity.ok("Hello Bus");
//    }

    @GetMapping("/getBus")
    public ResponseEntity<?> getBus(Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") )){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        List<BusDTO> busDTOList = busService.getAllBuses();
        return ResponseEntity.ok(busDTOList);
    }

    @PutMapping("/updateBus")
    public ResponseEntity<?> updateBus(@RequestBody BusDTO busDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("bus-owner"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or bus-owner role.");
        }
        BusDTO bus = busService.updateBus(busDTO);
        return ResponseEntity.ok(bus);
    }

    @DeleteMapping("/deleteBus")
    public ResponseEntity<?> deleteBus(@RequestBody BusDTO busDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("bus-owner"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or bus-owner role.");
        }
        Boolean isDeleted = busService.deleteBus(busDTO);
        return ResponseEntity.ok(isDeleted);
    }

    @PostMapping("/saveBus")
    public ResponseEntity<?> saveBus(@RequestBody BusDTO busDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("bus-owner"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or bus-owner role.");
        }
        BusDTO bus = busService.saveBus(busDTO);
        return ResponseEntity.ok(bus);
    }




}
