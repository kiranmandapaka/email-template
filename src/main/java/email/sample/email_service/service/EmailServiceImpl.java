package email.sample.email_service.service;


import email.sample.email_service.entity.EmailMessage;
import email.sample.email_service.model.EmailMessageDto;
import email.sample.email_service.model.EmailTemplateData;
import email.sample.email_service.repository.EmailRepository;
import email.sample.email_service.repository.EmailRepositoryHelper;
import email.sample.email_service.util.EmailTemplates;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailRepositoryHelper emailRepositoryHelper;

    @Value("${spring.mail.username}")
    private String efundzmail;

    @Value("${efundz.unsubscribe.url}")
    private String unsubscribeUrl;
    @Override
    public void sendEmail(EmailMessageDto emailData) throws MessagingException {
        log.info("EmailMessageDto:"+emailData);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        EmailTemplateData templateData = emailTemplateService.getTemplateData(emailData.getEmailType());
        helper.setFrom(efundzmail);
        helper.setTo(emailData.getEmailId());
        helper.setSubject(templateData.emailSubject);
        Context context = new Context();
        context.setVariable("name", emailData.getName());
        context.setVariable("applicationNumber", emailData.getApplicationNumb());
        context.setVariable("loneType", emailData.getLoneType());
        context.setVariable("amount", emailData.getAmount());
        context.setVariable("bankName", emailData.getBankName());
        context.setVariable("emailId", emailData.getEmailId());
        context.setVariable("unsubscribeUrl",unsubscribeUrl.replace("{emailId}",emailData.getEmailId()));

        String htmlContent = templateEngine.process(templateData.templateName, context);
        helper.setText(htmlContent, true);
        try {
            mailSender.send(mimeMessage);
            emailData.setStatus(EmailTemplates.EMAIL_SENT);
        } catch (Exception e){
            emailData.setStatus(EmailTemplates.EMAIL_FAIL);
            log.error("Error sending email: {}", e.getMessage(), e);
        } finally {
            emailData.setCreatedTime(LocalDateTime.now());
            emailRepositoryHelper.saveEmailData(modelMapper.map(emailData, EmailMessage.class));
        }
        log.info("Email sent successfully to {}", emailData.getEmailId());
    }

    public boolean unsubscribeUser(String email) {
        emailRepositoryHelper.unsubscribeEmailId(email);
        return true;
    }
}
