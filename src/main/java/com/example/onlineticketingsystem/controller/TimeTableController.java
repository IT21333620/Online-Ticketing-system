package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.TimeTableDTO;
import com.example.onlineticketingsystem.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/timetable")
@CrossOrigin
public class TimeTableController {

    @Autowired
    private TimeTableService timeTableService;

    @PostMapping("/saveTimetable")
    public ResponseEntity<?> saveTimetable(@RequestBody TimeTableDTO timeTableDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        TimeTableDTO timeTable = timeTableService.saveTimetable(timeTableDTO);
        return ResponseEntity.ok(timeTable);
    }

    @GetMapping("/getTimetable")
    public ResponseEntity<?> getTimetable(Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("ticket-inspector"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or ticket-inspector role.");
        }
        List<TimeTableDTO> timeTableDTOList = timeTableService.getAllTimetable();
        return ResponseEntity.ok(timeTableDTOList);
    }

    @PutMapping("/updateTimetable")
    public ResponseEntity<?> updateTimetable(@RequestBody TimeTableDTO timeTableDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        TimeTableDTO timeTable = timeTableService.updateTimetable(timeTableDTO);
        return ResponseEntity.ok(timeTable);
    }

    @DeleteMapping("/deleteTimetable")
    public ResponseEntity<?> deleteTimetable(@RequestBody TimeTableDTO timeTableDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        Boolean isDeleted = timeTableService.deleteTimetable(timeTableDTO);
        return ResponseEntity.ok(isDeleted);
    }
}
