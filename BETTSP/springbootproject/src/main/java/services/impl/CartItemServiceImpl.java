package services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CartItemDao;
import data.dto.CartItemDTO;
import jakarta.persistence.EntityNotFoundException;
import services.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Override
    public void createManyCartItem(List<CartItemDTO> dtos) {
        cartItemDao.createManyCartItem(dtos);
    }

    @Override
    public void createOneCartItem(CartItemDTO dto) {
        cartItemDao.createOneCartItem(dto);
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
    public void updateOneCartItem(CartItemDTO dto) {
        cartItemDao.updateOneCartItem(dto);
    }

}
