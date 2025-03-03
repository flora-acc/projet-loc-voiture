package com.accenture.controller;

import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    List<AdminResponseDto> afficherAdmins() {
        return adminService.trouverTous();
    }

    @PostMapping
    public ResponseEntity<AdminResponseDto> creerAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(adminService.creerAdmin(adminRequestDto));
    }

    @DeleteMapping
    ResponseEntity<Void> supprimerAdmin(@RequestParam String email, @RequestParam String motDePasse) {
        adminService.supprimerAdmin(email, motDePasse);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // comportement correct, 204 No content = la ressource n'existe plus
    }

}
