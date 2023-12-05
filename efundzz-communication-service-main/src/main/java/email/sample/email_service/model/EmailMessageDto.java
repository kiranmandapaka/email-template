package email.sample.email_service.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class EmailMessageDto {
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
