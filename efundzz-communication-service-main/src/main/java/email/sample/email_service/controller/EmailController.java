package email.sample.email_service.controller;

import email.sample.email_service.model.EmailMessageDto;
import email.sample.email_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Slf4j
@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailMessageDto emailMessageDto) throws MessagingException, MessagingException {
        emailService.sendEmail(emailMessageDto);
        log.info("Email sent successfully.");
        return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully!");
    }
}
