package services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public CartDTO getOneCartByIdCustomer(Long idCustomer) throws EntityNotFoundException {
        CartDTO cart = cartDao.getOneCartByIdCustomer(idCustomer);
        List<CartItemDTO> listCartItem = cartItemDao.getManyCartItemByIdCart(cart.getId());
        cart.setListCartItem(listCartItem);
        return cart;

    }

    @Override
    public void updateOneCartWithoutTotalPrice(CartDTO dto) {
        CartDTO newCart = cartDao.getOneCartByIdCart(dto.getId());
        if (dto.getNotes() != null) {
            newCart.setNotes(dto.getNotes());
        }
        if (dto.getCodeDiscount() != null) {
            newCart.setCodeDiscount(dto.getCodeDiscount());
        }
        if (dto.getStatus() != null) {
            newCart.setStatus(dto.getStatus());
        }
        if (dto.getListCartItem() != null) {
            newCart.setListCartItem(dto.getListCartItem());
        }
        cartDao.updateOneCartWithoutTotalPrice(newCart);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

        // TODO tim hieu 1 code tuong ung voi phan tram
        String codeDiscount = cart.getCodeDiscount();
        Long codeDiscountToPercent = Long.parseLong(codeDiscount);

        Double totalPrice = tempPrice - ((Double.valueOf(codeDiscountToPercent) / 100) * tempPrice);

        cartDao.updateTotalPrice(String.valueOf(totalPrice), idCustomer);
    }

    @Override
    public CartDTO updateTotalPriceAndGetCart(Long idCustomer) {
        updateTotalPrice(idCustomer);
        return getOneCartByIdCustomer(idCustomer);
    }

}
