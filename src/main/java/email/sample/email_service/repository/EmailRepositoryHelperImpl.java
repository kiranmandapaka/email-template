package email.sample.email_service.repository;

import email.sample.email_service.entity.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EmailRepositoryHelperImpl implements EmailRepositoryHelper{

    @Autowired
    private EmailRepository emailRepository;
   @Transactional
    public void saveEmailData(EmailMessage emailMessage) {
        emailRepository.save(emailMessage);
    }

    @Transactional
    public void unsubscribeEmailId(String emailId) {
        List<EmailMessage> listData = emailRepository.deleteByEmailId(emailId);
    }
}
