package email.sample.email_service.service;

import email.sample.email_service.model.EmailMessageDto;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {
    void sendEmail(EmailMessageDto emailMessageDto) throws MessagingException;
    boolean unsubscribeUser(String emailId);

}
