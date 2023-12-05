package email.sample.email_service.repository;

import email.sample.email_service.entity.EmailMessage;

public interface EmailRepositoryHelper {
    void saveEmailData(EmailMessage emailMessage);
    void unsubscribeEmailId(String emailId);
}
