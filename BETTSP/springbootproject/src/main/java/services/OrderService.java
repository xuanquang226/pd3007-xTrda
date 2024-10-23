package services;

import java.util.List;

import data.dto.OrderDTO;

public interface OrderService {
    void createOrderByIdCustomer(Long idCustomer);

    OrderDTO getOneOrder(Long id);

    List<OrderDTO> getManyOrder();

    void deleteOrder(Long id);

    void updateOrderLine(Long idCustomer);
}
