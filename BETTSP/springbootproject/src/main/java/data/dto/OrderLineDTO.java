package data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDTO extends BaseDTO {
    private Long id;
    private Long idOrder;
    private Long idProduct;
    private String name;
    private Long quantity;
    private String price;
    private String note;
}
