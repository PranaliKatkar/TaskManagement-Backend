package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // GET all users (ADMIN only)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

    // DELETE user by ID (ADMIN only)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        if (!userRepo.existsById(id)) {
            return ResponseEntity
                    .status(404)
                    .body("User not found");
        }

        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
