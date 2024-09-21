package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.RouteDTO;
import com.example.onlineticketingsystem.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/route")
@CrossOrigin
public class RouteController {
    @Autowired
    private RouteService routeService;

    @PostMapping("/saveRoute")
    public ResponseEntity<?> saveRoute(@RequestBody RouteDTO routeDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        RouteDTO route = routeService.saveRoute(routeDTO);
        return ResponseEntity.ok(route);
    }

    @GetMapping("/getRoute")
    public ResponseEntity<?> getRoute(Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        List<RouteDTO> route = routeService.getAllRoutes();
        return ResponseEntity.ok(route);
    }

    @PutMapping("/updateRoute")
    public ResponseEntity<?> updateRoute(@RequestBody RouteDTO routeDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        RouteDTO route = routeService.updateRoute(routeDTO);
        return ResponseEntity.ok(route);
    }


    @DeleteMapping("/deleteRoute")
    public ResponseEntity<?> deleteRoute(@RequestBody RouteDTO routeDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        Boolean route = routeService.deleteRoute(routeDTO);
        return ResponseEntity.ok(route);
    }
}