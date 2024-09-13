package com.example.onlineticketingsystem.DTO;

import com.example.onlineticketingsystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String name;
    private String email;
    private String contactNo;
    private String password;
    private Role role;

    private int balance; // for passenger

    private int inspectorId; // for ticket inspector

    private int registrationNo; // for bus owner
    private String ownedBuses; // for bus owner

    private String type; // for all
}
