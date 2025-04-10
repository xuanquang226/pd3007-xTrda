package data.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import data.dto.OrderDTO;

public interface OrderDao {
    void createOrderByIdCustomer(OrderDTO dto);

    OrderDTO getOneOrder(Long id);

    OrderDTO getOneOrderByIdCustomer(Long idCustomer);

    List<OrderDTO> getManyOrder();

    void deleteOrder(Long id);

    List<OrderDTO> getManyOrderByIdCustomer(Long idCustomer);

    Page<OrderDTO> getManyOrderByIdCustomer(Long idCustomer, int page, int size);
}
