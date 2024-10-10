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
public class OrderDTO extends BaseDTO {
    private Long id;
    private Long idCustomer;
    private String totalPrice;
    private String status;
    private String codeDiscount;
    private String note;
    private List<OrderLineDTO> listOrderLine;
}
