package services;

import java.util.List;

import data.dto.CartItemDTO;
import data.dto.OrderLineDTO;

public interface OrderLineService {
    void createManyOrderLine(Long idOrder, List<CartItemDTO> cartItems);

    List<OrderLineDTO> getManyOrderLineByIdOrder(Long idOrder);

    List<OrderLineDTO> getManyOrderLineByIdOrderList(List<Long> idOrderList);
}
