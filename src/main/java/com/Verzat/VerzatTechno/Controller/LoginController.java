package com.Verzat.VerzatTechno.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Service.LoginService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://task-management-frontend-jk35.onrender.com")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return loginService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return loginService.login(user);
    }
}
