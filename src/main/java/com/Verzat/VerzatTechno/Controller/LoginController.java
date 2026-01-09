package com.Verzat.VerzatTechno.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Dto.AdminResponse;
import com.Verzat.VerzatTechno.Dto.UserResponse;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Service.LoginService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody User user) {

        User savedUser = loginService.register(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                "USER"
        );
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user) {

        if ("admin@gmail.com".equals(user.getEmail())
                && "admin".equals(user.getPassword())) {
            return new AdminResponse("admin", "ADMIN");
        }

        User dbUser = loginService.login(user);

        return new UserResponse(
                dbUser.getId(),
                dbUser.getUsername(),
                dbUser.getEmail(),
                dbUser.getPhoneNumber(),
                "USER"
        );
    }
}
