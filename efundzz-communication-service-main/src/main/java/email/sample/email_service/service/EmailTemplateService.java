package email.sample.email_service.service;

import email.sample.email_service.util.EmailTemplates;
import email.sample.email_service.model.EmailTemplateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailTemplateService {

    public EmailTemplateData getTemplateData(String templateType) {
        String templateName;
        String emailSubject;
        switch (templateType) {
            case "EFUNDZZ_THANKYOU":
                templateName = EmailTemplates.EFUNDZZ_THANKYOU;
                emailSubject = EmailTemplates.THANKYOU_SUBJECT;
                break;
            case "EFUNDZZ_REMINDER":
                templateName = EmailTemplates.EFUNDZZ_REMINDER;
                emailSubject = EmailTemplates.REMINDER_SUBJECT;
                break;
            case "EFUNDZZ_REGRET":
                templateName = EmailTemplates.EFUNDZZ_REGRET;
                emailSubject = EmailTemplates.REGRET_SUBJECT;
                break;
            case "ROBO_THANKYOU":
                templateName = EmailTemplates.ROBO_THANKYOU;
                emailSubject = EmailTemplates.THANKYOU_SUBJECT;
                break;
            case "ROBO_REMINDER":
                templateName = EmailTemplates.ROBO_REMINDER;
                emailSubject = EmailTemplates.REMINDER_SUBJECT;
                break;
            case "ROBO_REGRET":
                templateName = EmailTemplates.ROBO_REGRET;
                emailSubject = EmailTemplates.REGRET_SUBJECT;
                break;
            case "VAHAK_THANKYOU":
                templateName = EmailTemplates.VAHAK_THANKYOU;
                emailSubject = EmailTemplates.THANKYOU_SUBJECT;
                break;
            case "VAHAK_REMINDER":
                templateName = EmailTemplates.VAHAK_REMINDER;
                emailSubject = EmailTemplates.REMINDER_SUBJECT;
                break;
            case "CUSTOMER_FORM":
                templateName = EmailTemplates.CUSTOMER_FORM;
                emailSubject = EmailTemplates.THANKYOU_SUBJECT_VAHAK;
                break;
            case "VAHAK_AGENT":
                templateName = EmailTemplates.VAHAK_AGENT;
                emailSubject = EmailTemplates.NEW_LEAD_VAHAK;
                break;


            default:
                log.error("Invalid template type: {}", templateType);
                throw new IllegalArgumentException("Invalid template type: " + templateType);
        }

        return new EmailTemplateData(templateName, emailSubject);
    }

}
