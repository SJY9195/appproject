package com.ohgiraffers.jenkins_test_app.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AuthAdminController {

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
