package com.example.resourceserver.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/account")
    public String account(JwtAuthenticationToken principal) {
        return "Hello, " + principal.getAuthorities();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Admin panel";
    }

    @GetMapping("/home")
    public String home() {
        return "Home page";
    }

}
