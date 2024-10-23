package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dto.CartDTO;
import jakarta.persistence.EntityNotFoundException;
import services.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{idCustomer}")
    public ResponseEntity<CartDTO> getOneCartByIdCustomer(@PathVariable Long idCustomer) {
        try {
            CartDTO cart = cartService.getOneCartByIdCustomer(idCustomer);
            return ResponseEntity.ok(cart);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createOneCart(@RequestBody CartDTO cart) {
        cartService.createOneCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // @PutMapping("/{idCustomer}")
    // public ResponseEntity<Void> updateTotalPrice(@PathVariable Long idCustomer) {
    // cartService.updateTotalPrice(idCustomer);
    // return ResponseEntity.noContent().build();
    // }

    @PutMapping
    public ResponseEntity<Void> updateCart(@RequestBody CartDTO cart) {
        cartService.updateOneCartWithoutTotalPrice(cart);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idCustomer}")
    public ResponseEntity<Void> updateCartAfterOrder(@PathVariable Long idCustomer) {
        cartService.updateCartAfterOrder(idCustomer);
        return ResponseEntity.noContent().build();
    }

}
