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
public class CartDTO extends BaseDTO {
    private Long id;
    private Long idCustomer;
    private String status;
    private String totalPrice;
    private String codeDiscount;
    private String notes;
    private List<CartItemDTO> listCartItem;
}
