package data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO extends BaseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String body;
    private String ticket;
}
