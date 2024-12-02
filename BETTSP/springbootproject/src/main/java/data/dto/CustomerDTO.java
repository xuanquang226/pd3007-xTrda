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
public class CustomerDTO extends BaseDTO {
    private Long id;
    private String name;
    private String mail;
    private String location;
    private String phone;
    private Long idAccount;
    private List<OrderDTO> listOrder;
}
