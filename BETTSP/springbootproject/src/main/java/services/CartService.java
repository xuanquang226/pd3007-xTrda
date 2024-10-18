package services;

import data.dto.CartDTO;

public interface CartService {
    CartDTO getOneCartByIdCart(Long id);

    CartDTO getOneCartByIdCustomer(Long idCustomer);

    void updateOneCartWithoutTotalPrice(CartDTO dto);

    void createOneCart(CartDTO dto);

    void updateTotalPrice(Long idCustomer);

    CartDTO updateTotalPriceAndGetCart(Long idCustomer);
}
