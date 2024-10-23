package services.impl;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CartItemDao;
import data.dao.ProductDao;
import data.dto.CartItemDTO;
import data.dto.ProductDTO;
import jakarta.persistence.EntityNotFoundException;
import services.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public void createManyCartItem(List<CartItemDTO> dtos) {
        List<CartItemDTO> newDtos = new ArrayList<>();
        for (CartItemDTO dto : dtos) {
            try {
                if (dto.getIdProduct() != null) {
                    newDtos.add(dto);
                } else {
                    throw new IllegalArgumentException("CartItemDto can't be null");
                }
            } catch (IllegalArgumentException eArgumentException) {
                System.getLogger(CartItemServiceImpl.class.getName()).log(Level.INFO, eArgumentException);
            }
        }
        cartItemDao.createManyCartItem(newDtos);
    }

    @Override
    public void createOneCartItem(CartItemDTO dto) {
        // Inserting the name of product and price after adding it to the cart
        // Validate the value before creating the cart item
        CartItemDTO cartItemDTO = dto;
        ProductDTO productDTO = null;
        try {
            productDTO = productDao.getOneProduct(dto.getIdProduct());
            cartItemDTO.setName(productDTO.getName());
            String price = (Long.parseLong(productDTO.getPrice()) * dto.getQuantity()) + "";
            cartItemDTO.setPrice(price);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw new IllegalArgumentException();
        }

        if (cartItemDTO.getQuantity() > productDTO.getQuantity()) {
            throw new IllegalArgumentException();
        }

        if (cartItemDTO.getIdProduct() != null) {
            cartItemDao.createOneCartItem(cartItemDTO);
        } else {
            throw new IllegalArgumentException("CartItemDto can't be null");
        }
    }

    @Override
    public void deleteManyCartItem(List<Long> ids) {
        cartItemDao.deleteManyCartItem(ids);
    }

    @Override
    public void deleteOneCartItem(Long id) {
        cartItemDao.deleteOneCartItem(id);
    }

    @Override
    public List<CartItemDTO> getAllCartItem() {
        return cartItemDao.getAllCartItem();
    }

    @Override
    public CartItemDTO getOneCartItem(Long id) {
        try {
            CartItemDTO cartItemDTO = cartItemDao.getOneCartItem(id);
            return cartItemDTO;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("CartItem with id " + id + " not found");
        }
    }

    @Override
    public void updateManyCartItem(List<CartItemDTO> dtos) {
        cartItemDao.updateManyCartItem(dtos);

    }

    @Override
    public void updateOneCartItem(CartItemDTO dto) throws IllegalArgumentException, EntityNotFoundException {

        CartItemDTO newDto = cartItemDao.getOneCartItemByIdProductAndIdCart(dto.getIdProduct(), dto.getIdCart());
        ProductDTO productDTO = productDao.getOneProduct(newDto.getIdProduct());

        if (dto.getQuantity() != null) {
            if (dto.getQuantity() > productDTO.getQuantity()) {
                throw new IllegalArgumentException();
            }
            newDto.setQuantity(dto.getQuantity());
            String newPrice = Long.parseLong(productDTO.getPrice().toString()) * dto.getQuantity() + "";
            newDto.setPrice(newPrice);
        }
        if (dto.getNote() != null) {
            newDto.setNote(dto.getNote());
        }
        cartItemDao.updateOneCartItem(newDto);
    }

    @Override
    public List<CartItemDTO> getAllCartItemByIdCart(Long idCart) {
        return cartItemDao.getManyCartItemByIdCart(idCart);
    }
}
