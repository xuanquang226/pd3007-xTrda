package data.dao;

import java.util.List;

import data.dto.OrderLineDTO;

public interface OrderLineDao {
    void createManyOrderLine(List<OrderLineDTO> orderLine);

    List<OrderLineDTO> getManyOrderLine(Long idOrder);

    List<OrderLineDTO> getManyOrderLineByIdOrderList(List<Long> idOrderList);
}
