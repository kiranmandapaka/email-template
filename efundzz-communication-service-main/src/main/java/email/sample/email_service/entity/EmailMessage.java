package email.sample.email_service.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_message")
@Data
@RequiredArgsConstructor
public class EmailMessage {
    @Id
    private String applicationNumb;

    private String name;

    private String emailId;

    private String loneType;

    private String amount;

    private String bankName;

    private String emailType;

    private LocalDateTime createdTime;

    private String status;
}
