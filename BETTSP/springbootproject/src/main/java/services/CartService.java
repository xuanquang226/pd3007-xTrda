package services;

import data.dto.CartDTO;

public interface CartService {
    CartDTO getOneCartByIdCart(Long id);

    CartDTO getOneCartByIdCustomer(Long idCustomer);

    void updateOneCart(CartDTO dto);

    void createOneCart(CartDTO dto);

    void updateTotalPrice(Long idCustomer);

    void updateStatus();

    void updateCodeDiscount();

    void updateNotes();
}
