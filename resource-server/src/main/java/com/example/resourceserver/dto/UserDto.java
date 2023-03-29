package com.example.resourceserver.dto;

import com.example.resourceserver.models.UserProvider;
import lombok.Data;

@Data
public class UserDto {

    private int id;

    private String email;

    private String password;

    private String name;

    private String image;

    private UserProvider provider;

}

