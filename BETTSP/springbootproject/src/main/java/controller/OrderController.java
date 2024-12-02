package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import services.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
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
}
