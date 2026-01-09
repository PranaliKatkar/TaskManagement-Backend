package com.Verzat.VerzatTechno.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Dto.UserResponse;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.UserRepo;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(
    origins = {
        "https://task-management-frontend-jk35.onrender.com",
        "http://localhost:3000"
    }
)
public class AdminController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userRepo.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        "USER"
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        if (!userRepo.existsById(id)) {
            return ResponseEntity.status(404).body("User not found");
        }

        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
