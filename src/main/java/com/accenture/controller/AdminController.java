package com.accenture.controller;

import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    List<AdminResponseDto> listeAdmins() {
        return adminService.trouverTous();
    }


    @PostMapping("/creation")
    public ResponseEntity<AdminResponseDto> creation(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(adminService.creerAdmin(adminRequestDto));
    }
}
