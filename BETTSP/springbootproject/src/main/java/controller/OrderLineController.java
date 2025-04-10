package controller;

import java.util.List;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dto.OrderLineDTO;
import services.OrderLineService;

@RestController
@RequestMapping("/api/order-line")
public class OrderLineController {
    @Autowired
    private OrderLineService orderLineService;

    @GetMapping("/{idOrder}")
    public ResponseEntity<List<OrderLineDTO>> getManyOrderLine(@PathVariable Long idOrder) {
        return ResponseEntity.ok(orderLineService.getManyOrderLineByIdOrder(idOrder));
    }

    @PostMapping()
    public ResponseEntity<Void> updateOrderLine() {
        return ResponseEntity.noContent().build();
    }
}
