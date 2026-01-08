package com.Verzat.VerzatTechno.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Verzat.VerzatTechno.Service.EmailService;

@RestController
public class MailTestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/api/mail-test")
    public String testMail() throws Exception {
        emailService.sendHtmlEmail(
            "pranalikatkar000@gmail.com",
            "TEST MAIL",
            "<h3>If you receive this, email works</h3>"
        );
        return "MAIL_SENT";
    }
}
