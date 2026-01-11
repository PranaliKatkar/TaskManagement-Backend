package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Dto.UserResponse;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.UserRepo;
import com.Verzat.VerzatTechno.Service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getContactNumber(),
                        "USER"
                ))
                .toList();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUserCompletely(id);
    }
}
