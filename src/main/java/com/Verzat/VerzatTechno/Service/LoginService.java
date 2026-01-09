package com.Verzat.VerzatTechno.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.UserRepo;
import com.Verzat.VerzatTechno.Util.ContactNumberUtil;

@Service
public class LoginService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
            String formattedNumber = ContactNumberUtil.formatIndianNumber(user.getPhoneNumber());
            user.setPhoneNumber(formattedNumber);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User login(User user) {

        User dbUser = userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return dbUser;
    }
}
