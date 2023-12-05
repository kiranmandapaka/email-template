package email.sample.email_service.repository;


import email.sample.email_service.entity.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailMessage, Long> {
    List<EmailMessage> findByStatus(String status);
    List<EmailMessage> deleteByEmailId(String emailId);
}
