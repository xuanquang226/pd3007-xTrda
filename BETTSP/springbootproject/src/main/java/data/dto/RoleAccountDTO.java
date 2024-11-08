package data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleAccountDTO extends BaseDTO {

    private Long id;

    private Long idAccount;

    private Long idRole;
}
