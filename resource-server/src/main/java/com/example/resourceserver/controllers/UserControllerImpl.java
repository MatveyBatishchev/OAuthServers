package com.example.resourceserver.controllers;


import com.example.resourceserver.dto.UserDto;
import com.example.resourceserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

}
