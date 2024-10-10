package data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO extends BaseDTO {
    private Long id;
    private String userName;
    private String password;
    private String accountType;
    private Long idCustomer;
}
