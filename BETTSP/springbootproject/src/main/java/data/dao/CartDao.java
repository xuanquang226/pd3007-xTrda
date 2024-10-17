package data.dao;

import data.dto.CartDTO;

public interface CartDao {

    CartDTO getOneCartByIdCart(Long id);

    CartDTO getOneCartByIdCustomer(Long idCustomer);

    void updateOneCart(CartDTO dto);

    void createOneCart(CartDTO dto);

    void updateTotalPrice(String totalPrice, Long idCustomer);

    void updateStatus();

    void updateCodeDiscount();

    void updateNotes();
}
