package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dto.OrderDTO;
import services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<Void> createOrder() {
        orderService.createOrderByIdCustomer();
        return ResponseEntity.noContent().build();
    }

    // @PostMapping("/{idCustomer}")
    // public ResponseEntity<Void> getOrder(@PathVariable Long idCustomer) {
    // orderService.updateOrderLine(idCustomer);
    // return ResponseEntity.noContent().build();
    // }

    // @GetMapping()
    // public ResponseEntity<List<OrderDTO>> getManyOrder() {
    // List<OrderDTO> orderList = orderService.getManyOrder();
    // if (orderList == null) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    // }
    // return ResponseEntity.ok(orderList);
    // };

    @GetMapping()
    public ResponseEntity<Page<OrderDTO>> getManyOrder(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<OrderDTO> orderList = orderService.getOrderList(page, size);
        return ResponseEntity.ok(orderList);
    };
}
