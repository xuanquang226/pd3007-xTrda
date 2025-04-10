package data.dto;

import java.util.List;

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
    private List<RoleAccountDTO> roleAccountList;
    private String status;
}
