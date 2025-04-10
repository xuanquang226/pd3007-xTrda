package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dto.CartItemDTO;
import jakarta.persistence.EntityNotFoundException;
import services.CartItemService;

@RestController
@RequestMapping("/api/cart-item")
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

    @GetMapping("/many/{idCart}")
    public ResponseEntity<List<CartItemDTO>> getManyCartItemByIdCart(@PathVariable Long idCart) {
        List<CartItemDTO> listCartItem = cartItemService.getAllCartItemByIdCart(idCart);
        return ResponseEntity.ok(listCartItem);
    }

    @PostMapping
    public ResponseEntity<Void> createOneCartItem(@RequestBody CartItemDTO cartItemDTO) {
        try {
            cartItemService.createOneCartItem(cartItemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/many")
    public ResponseEntity<Void> createManyCartItem(@RequestBody List<CartItemDTO> listCartItem) {
        try {
            cartItemService.createManyCartItem(listCartItem);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneCartItem(@PathVariable Long id) {
        cartItemService.deleteOneCartItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/many")
    public ResponseEntity<Void> deleteManyCartItem(@RequestBody List<Long> ids) {
        cartItemService.deleteManyCartItem(ids);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateOneCartItem(@RequestBody CartItemDTO dto) {
        try {
            cartItemService.updateOneCartItem(dto);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/many")
    public ResponseEntity<Void> updateManyCartItem(@RequestBody List<CartItemDTO> dtos) {
        cartItemService.updateManyCartItem(dtos);
        return ResponseEntity.noContent().build();
    }
}
