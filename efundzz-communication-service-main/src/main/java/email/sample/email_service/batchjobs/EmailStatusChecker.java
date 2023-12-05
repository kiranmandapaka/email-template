package email.sample.email_service.batchjobs;


import email.sample.email_service.entity.EmailMessage;
import email.sample.email_service.model.EmailMessageDto;
import email.sample.email_service.repository.EmailRepository;
import email.sample.email_service.repository.EmailRepositoryHelper;
import email.sample.email_service.service.EmailServiceImpl;
import email.sample.email_service.util.EmailTemplates;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmailStatusChecker {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private EmailServiceImpl emailServiceimpl;
    @Autowired
    private EmailRepositoryHelper emailRepositoryHelper;
    @Autowired
    private ModelMapper modelMapper;

    @Scheduled(cron = "0 0 8-23/1,8 * * *")
    public void resendFailedEmails() {
        List<EmailMessage> failedEmails = emailRepository.findByStatus(EmailTemplates.EMAIL_FAIL);
        for (EmailMessage email : failedEmails) {
            try {
                    emailServiceimpl.sendEmail(modelMapper.map(email, EmailMessageDto.class));
                    email.setStatus(EmailTemplates.EMAIL_SENT);
            } catch (Exception e) {
                email.setStatus(EmailTemplates.EMAIL_FAIL);
                log.error("Error sending email: {}", e.getMessage(), e);
                continue;
            }
            email.setCreatedTime(LocalDateTime.now());
            emailRepositoryHelper.saveEmailData(email);
        }
    }

    @Scheduled(cron = "0 0 8-20/1,8 * * *")
    public void SendHourlyReminderEmails() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<EmailMessage> emails = emailRepository.findByStatus(EmailTemplates.EMAIL_REMINDER);
        for (EmailMessage email : emails) {
            LocalDateTime lastSendTime = email.getCreatedTime();
            if (Duration.between(lastSendTime, currentTime).toHours() == 1 && email.getStatus() == EmailTemplates.EMAIL_REMINDER){
                try {
                    emailServiceimpl.sendEmail(modelMapper.map(email, EmailMessageDto.class));
                    email.setStatus(EmailTemplates.EMAIL_REMINDER_1);
                } catch (Exception e) {
                    email.setStatus(EmailTemplates.EMAIL_REMINDER);
                    log.error("Error sending email: {}", e.getMessage(), e);
                }
                emailRepositoryHelper.saveEmailData(email);
            }
        }
    }

   @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyReminderEmails() throws MessagingException {
        LocalDateTime currentTime = LocalDateTime.now();
        List<EmailMessage> emails = emailRepository.findByStatus(EmailTemplates.EMAIL_REMINDER_1);
        for (EmailMessage email : emails) {
            LocalDateTime lastSendTime = email.getCreatedTime();
            if (Duration.between(lastSendTime, currentTime).toDays() == 1 && email.getStatus() == EmailTemplates.EMAIL_REMINDER_1) {
                try {
                    emailServiceimpl.sendEmail(modelMapper.map(email, EmailMessageDto.class));
                    email.setStatus(EmailTemplates.EMAIL_REMINDER_1);
                } catch (Exception e) {
                    email.setStatus(EmailTemplates.EMAIL_REMINDER_1);
                    log.error("Error sending email: {}", e.getMessage(), e);
                }
                emailRepositoryHelper.saveEmailData(email);
            }
            else if (Duration.between(lastSendTime, currentTime).toDays() == 3 && email.getStatus() == EmailTemplates.EMAIL_REMINDER_1) {
                try {
                    emailServiceimpl.sendEmail(modelMapper.map(email, EmailMessageDto.class));
                    email.setStatus(EmailTemplates.EMAIL_SENT);
                } catch (Exception e) {
                    email.setStatus(EmailTemplates.EMAIL_REMINDER_1);
                    log.error("Error sending email: {}", e.getMessage(), e);
                }
                emailRepositoryHelper.saveEmailData(email);
            }
        }
    }
}
