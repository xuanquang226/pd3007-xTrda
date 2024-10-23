package data.dao;

import java.util.List;

import data.dto.OrderDTO;

public interface OrderDao {
    void createOrderByIdCustomer(OrderDTO dto);

    OrderDTO getOneOrder(Long id);

    OrderDTO getOneOrderByIdCustomer(Long idCustomer);

    List<OrderDTO> getManyOrder();

    void deleteOrder(Long id);
}
