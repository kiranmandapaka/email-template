package email.sample.email_service.controller;

import email.sample.email_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class EmailUnsubscribeController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/unsubscribe/{emailId}")
    public ResponseEntity<String> unsubscribeUser(@PathVariable String emailId) {
        boolean success = emailService.unsubscribeUser(emailId);
        if (success) {
            return ResponseEntity.ok("Unsubscribed successfully");
        } else {
            return ResponseEntity.badRequest().body("Unsubscription failed");
        }
    }
}
