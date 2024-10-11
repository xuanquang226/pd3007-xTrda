package data.dao;

import java.util.List;

import data.dto.CartItemDTO;

public interface CartItemDao {
    void createOneCartItem(CartItemDTO dto);

    void createManyCartItem(List<CartItemDTO> dtos);

    List<CartItemDTO> getAllCartItem();

    CartItemDTO getOneCartItem(Long id);

    void updateOneCartItem(CartItemDTO dto);

    void updateManyCartItem(List<CartItemDTO> dtos);

    void deleteOneCartItem(Long id);

    void deleteManyCartItem(List<Long> ids);
}
