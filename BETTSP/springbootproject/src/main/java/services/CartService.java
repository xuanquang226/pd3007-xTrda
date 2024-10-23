package services;

import data.dto.CartDTO;

public interface CartService {
    CartDTO getOneCartByIdCart(Long id);

    CartDTO getOneCartByIdCustomer(Long idCustomer);

    void updateOneCartWithoutTotalPrice(CartDTO dto);

    void createOneCart(CartDTO dto);

    void processCart(Long idCustomer);

    // CartDTO updateTotalPriceAndGetCart(Long idCustomer);

    void updateCartAfterOrder(Long idCustomer);
}
