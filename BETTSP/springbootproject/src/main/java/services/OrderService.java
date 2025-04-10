package services;

import java.util.List;

import org.springframework.data.domain.Page;

import data.dto.OrderDTO;

public interface OrderService {
    void createOrderByIdCustomer();

    OrderDTO getOneOrder(Long id);

    List<OrderDTO> getManyOrder();

    void deleteOrder(Long id);

    Page<OrderDTO> getOrderList(int page, int size);

}
