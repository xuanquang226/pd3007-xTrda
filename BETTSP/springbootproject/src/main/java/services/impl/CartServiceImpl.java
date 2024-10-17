package services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CartDao;
import data.dao.CartItemDao;
import data.dto.CartDTO;
import data.dto.CartItemDTO;
import jakarta.persistence.EntityNotFoundException;
import services.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Override
    public void createOneCart(CartDTO dto) {
        cartDao.createOneCart(dto);
    }

    @Override
    public CartDTO getOneCartByIdCart(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CartDTO getOneCartByIdCustomer(Long idCustomer) throws EntityNotFoundException {
        CartDTO cart = cartDao.getOneCartByIdCustomer(idCustomer);
        List<CartItemDTO> listCartItem = cartItemDao.getManyCartItemByIdCart(cart.getId());
        cart.setListCartItem(listCartItem);
        return cart;
    }

    @Override
    public void updateOneCart(CartDTO dto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateCodeDiscount() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateNotes() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateStatus() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateTotalPrice(Long idCustomer) {
        // CartDTO cart = getOneCartByIdCart(idCustomer); bị null
        // List<CartItemDTO> listCartItem = cart.getListCartItem();
        CartDTO cart = cartDao.getOneCartByIdCustomer(idCustomer);
        List<CartItemDTO> listCartItem = cartItemDao.getManyCartItemByIdCart(cart.getId());
        Long tempPrice = 0L;
        for (CartItemDTO cartItem : listCartItem) {
            tempPrice += cartItem.getPrice();
        }

        String totalPrice = String.valueOf(tempPrice);
        cartDao.updateTotalPrice(totalPrice, idCustomer);
    }

}
