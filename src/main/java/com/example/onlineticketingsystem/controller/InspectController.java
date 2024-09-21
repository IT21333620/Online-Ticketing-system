package com.example.onlineticketingsystem.controller;

import com.example.onlineticketingsystem.DTO.InspectDTO;
import com.example.onlineticketingsystem.entity.Inspect;
import com.example.onlineticketingsystem.service.InspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "api/Inspect")
public class InspectController {

    @Autowired
    private InspectService inspectService;


    @PostMapping("/createInspect")
    public ResponseEntity<?> saveInspect (@RequestBody InspectDTO inspectDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        InspectDTO inspect = inspectService.saveInspect(inspectDTO);
        return ResponseEntity.ok(inspect);
    }

    @GetMapping("/getInspect")
    public ResponseEntity<?> getAllInspects(Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("ticket-inspector"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or ticket-inspector role.");
        }
        List<InspectDTO> inspects = inspectService.getAllInspects();
        return ResponseEntity.ok(inspects);
    }

    @GetMapping("/byInspector/{id}")
    public ResponseEntity<?> getInspectsByInspectorId(@PathVariable int id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ticket-inspector") || grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ticket-inspector Or admin role.");
        }
        List<Inspect> inspects = inspectService.getInspectsByInspectorId(id);
        return ResponseEntity.ok(inspects);
    }

    @DeleteMapping("/deleteInspect")
    public ResponseEntity<?> delete(@RequestBody InspectDTO inspectDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        Boolean isDeleted = inspectService.delete(inspectDTO);
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping("/updateInspect")
    public ResponseEntity<?> updateInspect(@RequestBody InspectDTO inspectDTO, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        InspectDTO inspect = inspectService.updateInspect(inspectDTO);
        return ResponseEntity.ok(inspect);
    }

    @GetMapping("/fraudCount")
    public ResponseEntity<?> fraudCount(Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("ticket-inspector"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or ticket-inspector role.");
        }
        int fraudCount = inspectService.fraudCount();
        return ResponseEntity.ok(fraudCount);
    }

    @GetMapping("/fraudByRoute")
    public ResponseEntity<?> fraudByRoute(Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("ticket-inspector"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin Or ticket-inspector role.");
        }
        Map<Integer, Integer> fraudByRoute = inspectService.fraudByRoute();
        return ResponseEntity.ok(fraudByRoute);
    }

    @GetMapping("/countTodayInspectors")
    public ResponseEntity<?> getCountOfInspectorIdByRouteToday(Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        Map<Integer, Long> countOfInspectorIdByRouteToday = inspectService.getCountOfInspectorIdByRouteToday();
        return ResponseEntity.ok(countOfInspectorIdByRouteToday);
    }

    @GetMapping("/getInspectorHistory/{id}")
    public ResponseEntity<?> getInspectHistoryByInspectorId(@PathVariable int id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ticket-inspector") || grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ticket-inspector Or admin role.");
        }
        List<Inspect> inspects = inspectService.getInspectHistoryByInspectorId(id);
        return ResponseEntity.ok(inspects);
    }

    @GetMapping("/getInspectorUpComing/{id}")
    public ResponseEntity<?> getUpComingInspectByInspectorId(@PathVariable int id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have admin role.");
        }
        List<Inspect> inspects = inspectService.getUpcomingInspectByInspectorId(id);
        return ResponseEntity.ok(inspects);
    }


}
