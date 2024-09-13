package com.example.onlineticketingsystem.security;

import com.example.onlineticketingsystem.entity.*;
import com.example.onlineticketingsystem.repo.BusOwnerRepo;
import com.example.onlineticketingsystem.repo.PassengerRepo;
import com.example.onlineticketingsystem.repo.TicketInspectorRepo;
import com.example.onlineticketingsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PassengerRepo passengerRepo;

    @Autowired
    private TicketInspectorRepo ticketInspectorRepo;

    @Autowired
    private BusOwnerRepo busOwnerRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find the user in each repository
        Passenger passenger = passengerRepo.findByEmail(email).orElse(null);
        if (passenger != null) {
            return new org.springframework.security.core.userdetails.User(
                    passenger.getEmail(),
                    passenger.getPassword(),
                    getAuthorities(passenger.getRole())
            );
        }

        BusOwner busOwner = busOwnerRepo.findByEmail(email).orElse(null);
        if (busOwner != null) {
            return new org.springframework.security.core.userdetails.User(
                    busOwner.getEmail(),
                    busOwner.getPassword(),
                    getAuthorities(busOwner.getRole())
            );
        }

        TicketInspector ticketInspector = ticketInspectorRepo.findByEmail(email).orElse(null);
        if (ticketInspector != null) {
            return new org.springframework.security.core.userdetails.User(
                    ticketInspector.getEmail(),
                    ticketInspector.getPassword(),
                    getAuthorities(ticketInspector.getRole())
            );
        }

        // If no user is found, throw exception
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }
}
