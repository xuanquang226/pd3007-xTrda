package data.dao;

import data.dto.CartDTO;

public interface CartDao {

    CartDTO getOneCartByIdCart(Long id);

    CartDTO getOneCartByIdCustomer(Long idCustomer);

    void updateOneCartWithoutTotalPrice(CartDTO dto);

    void createOneCart(CartDTO dto);

    void updateTotalPrice(String totalPrice, Long idCustomer);

    void updateCartAfterOrder(CartDTO dto);
}
