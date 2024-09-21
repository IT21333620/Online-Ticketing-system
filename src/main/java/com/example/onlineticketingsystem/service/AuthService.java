package com.example.onlineticketingsystem.service;

import com.example.onlineticketingsystem.DTO.AuthResponseDTO;
import com.example.onlineticketingsystem.DTO.LoginDTO;
import com.example.onlineticketingsystem.DTO.RegisterDTO;
import com.example.onlineticketingsystem.entity.*;
import com.example.onlineticketingsystem.repo.*;
import com.example.onlineticketingsystem.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private PassengerRepo passengerRepo;
    private TicketInspectorRepo ticketInspectorRepo;
    private BusOwnerRepo busOwnerRepo;
    private AdminRepo adminRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, PassengerRepo passengerRepo,
                       TicketInspectorRepo ticketInspectorRepo, BusOwnerRepo busOwnerRepo, AdminRepo adminRepo,
                       RoleRepo roleRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.passengerRepo = passengerRepo;
        this.ticketInspectorRepo = ticketInspectorRepo;
        this.busOwnerRepo = busOwnerRepo;
        this.adminRepo = adminRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    public void registerUser(RegisterDTO registerDTO) {
        // Common fields
        String email = registerDTO.getEmail();
        String password = passwordEncoder.encode(registerDTO.getPassword());
        String name = registerDTO.getName();
        String contactNo = registerDTO.getContactNo();

        Optional<Role> userRole = roleRepo.findById(registerDTO.getRole().getId());
        if (userRole.isEmpty()) {
            throw new RuntimeException("Role not found for the provided user type");
        }

        String type = registerDTO.getType().toUpperCase();

        switch (type) {
            case "P":
                Passenger passenger = new Passenger();
                passenger.setEmail(email);
                passenger.setPassword(password);
                passenger.setName(name);
                passenger.setContactNo(contactNo);
                passenger.setRole(userRole.get());
                passenger.setBalance(registerDTO.getBalance());
                passengerRepo.save(passenger);
                break;

            case "B":
                BusOwner busOwner = new BusOwner();
                busOwner.setEmail(email);
                busOwner.setPassword(password);
                busOwner.setName(name);
                busOwner.setContactNo(contactNo);
                busOwner.setRole(userRole.get());
                busOwner.setRegistrationNo(registerDTO.getRegistrationNo());
                busOwner.setOwnedBuses(registerDTO.getOwnedBuses());
                busOwnerRepo.save(busOwner);
                break;

            case "T":
                TicketInspector ticketInspector = new TicketInspector();
                ticketInspector.setEmail(email);
                ticketInspector.setPassword(password);
                ticketInspector.setName(name);
                ticketInspector.setContactNo(contactNo);
                ticketInspector.setRole(userRole.get());
                ticketInspector.setInspectorID(registerDTO.getInspectorId());
                ticketInspectorRepo.save(ticketInspector);
                break;

            case "A":
                Admin admin = new Admin();
                admin.setEmail(email);
                admin.setPassword(password);
                admin.setName(name);
                admin.setContactNo(contactNo);
                admin.setRole(userRole.get());
                adminRepo.save(admin);
                break;

            default:
                throw new IllegalArgumentException("Invalid user type provided");
        }
    }

    public AuthResponseDTO loginUser(LoginDTO loginDTO) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            return new AuthResponseDTO(token);
        } catch (BadCredentialsException e){
            throw new RuntimeException("Invalid username or password!");
        }
    }
}
