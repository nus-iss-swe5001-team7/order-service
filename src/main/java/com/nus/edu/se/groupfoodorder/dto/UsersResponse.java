package com.nus.edu.se.groupfoodorder.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {

    private UUID id;
    private String name;
    private String password;
    private String email;
    private String role;

}
