package email.sample.email_service.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class EmailTemplateData {
    public String templateName;
    public String emailSubject;

}
