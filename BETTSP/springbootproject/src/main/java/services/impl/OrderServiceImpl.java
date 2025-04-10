package services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.dao.OrderDao;
import data.dto.CartDTO;
import data.dto.OrderDTO;
import data.dto.OrderLineDTO;
import services.CartService;
import services.OrderLineService;
import services.OrderService;
import services.ProductService;
import utils.objects.AccountAuth;

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

    @Autowired
    private AccountAuth accountAuth;

    @Transactional
    @Override
    public void createOrderByIdCustomer() {
        // TODO: them ngay gio tao order
        CartDTO cart = cartService.getOneCartByIdCustomer();
        if (cart.getStatus().equalsIgnoreCase("inactive")) {
            throw new IllegalArgumentException("Gio hang trong");
        } else {
            OrderDTO order = new OrderDTO();
            order.setIdCustomer(accountAuth.getAccount().getIdCustomer());
            order.setNotes(cart.getNotes());
            order.setTotalPrice(cart.getTotalPrice());
            order.setCodeDiscount(cart.getCodeDiscount());
            order.setStatus("Paid");

            orderDao.createOrderByIdCustomer(order);

            OrderDTO orderAfterSave = orderDao.getOneOrderByIdCustomer(accountAuth.getAccount().getIdCustomer());
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
        // Mỗi order chưa có kèm theo orderline vì thế lấy list order để lấy lên list
        // orderline chưa sắp xếp.
        // Sau đó kiểm tra lại mỗi order line idOrder giống nhau thì cho vào cùng một
        // list map. Sau đó kiểm tra tương ứng thì add lại orderline
        // vào order.

        List<OrderDTO> orderDTOs = orderDao.getManyOrderByIdCustomer(accountAuth.getAccount().getIdCustomer());
        List<Long> idOrderList = orderDTOs.stream().map(OrderDTO::getId).collect(Collectors.toList());
        List<OrderLineDTO> listOrderLine = orderLineService.getManyOrderLineByIdOrderList(idOrderList);
        HashMap<Long, List<OrderLineDTO>> idOrderToOrderLineMap = new HashMap<>();
        listOrderLine.forEach(orderLine -> {
            // if (!idOrderToOrderLineMap.containsKey(orderLine.getIdOrder())) {
            // List<OrderLineDTO> orderLineList = new ArrayList<>();
            // orderLineList.add(orderLine);
            // idOrderToOrderLineMap.put(orderLine.getIdOrder(), orderLineList);
            // } else {
            // idOrderToOrderLineMap.get(orderLine.getIdOrder()).add(orderLine);
            // }
            idOrderToOrderLineMap.computeIfAbsent(orderLine.getIdOrder(), k -> new ArrayList<>()).add(orderLine);
        });

        orderDTOs.forEach(order -> {
            order.setListOrderLine(idOrderToOrderLineMap.get(order.getId()));
        });
        return orderDTOs;
    }

    @Override
    public OrderDTO getOneOrder(Long idCustomer) {
        return null;
    }

    @Override
    public Page<OrderDTO> getOrderList(int page, int size) {
        Page<OrderDTO> orderDTOs = orderDao.getManyOrderByIdCustomer(accountAuth.getAccount().getIdCustomer(),
                page,
                size);
        List<Long> idOrderList = orderDTOs.getContent().stream()
                .map(OrderDTO::getId) // Lấy ID của mỗi OrderDTO
                .collect(Collectors.toList());
        List<OrderLineDTO> listOrderLine = orderLineService.getManyOrderLineByIdOrderList(idOrderList);
        HashMap<Long, List<OrderLineDTO>> idOrderToOrderLineMap = new HashMap<>();
        listOrderLine.forEach(orderLine -> {
            idOrderToOrderLineMap.computeIfAbsent(orderLine.getIdOrder(), k -> new ArrayList<>()).add(orderLine);
        });

        orderDTOs.forEach(order -> {
            order.setListOrderLine(idOrderToOrderLineMap.get(order.getId()));
        });
        return orderDTOs;
    }
}
