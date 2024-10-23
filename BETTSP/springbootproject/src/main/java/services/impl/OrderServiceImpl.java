package services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.OrderDao;
import data.dto.CartDTO;
import data.dto.OrderDTO;
import jakarta.transaction.Transactional;
import services.CartService;
import services.OrderLineService;
import services.OrderService;
import services.ProductService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderLineService orderLineService;

    @Autowired
    private ProductService productService;

    @Transactional
    @Override
    public void createOrderByIdCustomer(Long idCustomer) {
        // TODO: them ngay gio tao order
        CartDTO cart = cartService.getOneCartByIdCustomer(idCustomer);
        if (cart.getStatus().equalsIgnoreCase("inactive")) {
            throw new IllegalArgumentException("Gio hang trong");
        } else {
            OrderDTO order = new OrderDTO();
            order.setIdCustomer(idCustomer);
            order.setNotes(cart.getNotes());
            order.setTotalPrice(cart.getTotalPrice());
            order.setCodeDiscount(cart.getCodeDiscount());
            order.setStatus("Paid");

            orderDao.createOrderByIdCustomer(order);

            OrderDTO orderAfterSave = orderDao.getOneOrderByIdCustomer(idCustomer);
            orderLineService.createManyOrderLine(orderAfterSave.getId(),
                    cart.getListCartItem());

            HashMap<Long, Long> productIdToQuantityMap = new HashMap<>();
            cart.getListCartItem().forEach(cartItem -> {
                productIdToQuantityMap.put(cartItem.getIdProduct(), cartItem.getQuantity());
            });
            productService.updateQuantityAfterOrder(productIdToQuantityMap);
        }
    }

    @Override
    public void deleteOrder(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<OrderDTO> getManyOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrderDTO getOneOrder(Long idCustomer) {
        return null;
    }

    @Override
    public void updateOrderLine(Long idCustomer) {
        CartDTO cart = cartService.getOneCartByIdCustomer(idCustomer);
        OrderDTO orderDTO = orderDao.getOneOrderByIdCustomer(idCustomer);
        orderLineService.createManyOrderLine(orderDTO.getId(), cart.getListCartItem());
    }

}
