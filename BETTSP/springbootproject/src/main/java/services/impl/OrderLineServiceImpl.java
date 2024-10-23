package services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.dao.OrderLineDao;
import data.dto.CartItemDTO;
import data.dto.OrderLineDTO;
import services.OrderLineService;

@Service
public class OrderLineServiceImpl implements OrderLineService {
    @Autowired
    private OrderLineDao orderLineDao;

    @Transactional
    @Override
    public void createManyOrderLine(Long idOrder, List<CartItemDTO> cartItems) {
        List<OrderLineDTO> orderLines = new ArrayList<>();
        cartItems.forEach((cartItem) -> {
            OrderLineDTO dto = new OrderLineDTO();
            dto.setIdOrder(idOrder);
            dto.setIdProduct(cartItem.getIdProduct());
            dto.setName(cartItem.getName());
            dto.setNote(cartItem.getNote());
            dto.setPrice(cartItem.getPrice().toString());
            dto.setQuantity(cartItem.getQuantity());
            orderLines.add(dto);
        });

        orderLineDao.createManyOrderLine(orderLines);
    }

    @Override
    public List<OrderLineDTO> getManyOrderLineByIdOrder(Long idOrder) {
        return orderLineDao.getManyOrderLine(idOrder);
    }

}
