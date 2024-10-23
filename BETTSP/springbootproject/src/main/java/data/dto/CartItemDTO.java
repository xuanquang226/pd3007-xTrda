package data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemDTO extends BaseDTO {
    private Long id;
    private Long idCart;
    private Long idProduct;
    private Long quantity;
    private String price;
    private String note;
    private String name;
}
