package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dto.CartItemDTO;
import jakarta.persistence.EntityNotFoundException;
import services.CartItemService;

@RestController
@RequestMapping("/cart-item")
@CrossOrigin(origins = "http://localhost:3000")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getOneCartItem(@PathVariable Long id) {
        try {
            CartItemDTO cartItemDTO = cartItemService.getOneCartItem(id);
            return ResponseEntity.ok(cartItemDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createOneCartItem(@RequestBody CartItemDTO cartItemDTO) {
        cartItemService.createOneCartItem(cartItemDTO);
        return ResponseEntity.noContent().build();
    }
}
