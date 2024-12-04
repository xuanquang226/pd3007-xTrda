package services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import data.dao.AccountDao;
import data.dao.CartDao;
import data.dao.CartItemDao;
import data.dto.AccountDTO;
import data.dto.CartDTO;
import data.dto.CartItemDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import services.CartService;
import utils.objects.AccountAuth;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private AccountAuth accountAuth;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createOneCart(CartDTO dto) {
        cartDao.createOneCart(dto);
    }

    @Override
    public CartDTO getOneCartByIdCart(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Transactional
    @Override
    public CartDTO getOneCartByIdCustomer() throws EntityNotFoundException {
        // TODO: Sử dụng security context để lấy name -> lấy id: Update: OK

        processCart(accountAuth.getAccount().getIdCustomer());
        CartDTO cart = cartDao.getOneCartByIdCustomer(accountAuth.getAccount().getIdCustomer());
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
            newCart.setStatus("active");
        }
        if (dto.getListCartItem() != null) {
            newCart.setListCartItem(dto.getListCartItem());
        }
        cartDao.updateOneCartWithoutTotalPrice(newCart);
    }

    // See
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void processCart(Long idCustomer) {
        CartDTO cart = cartDao.getOneCartByIdCustomer(idCustomer);
        List<CartItemDTO> listCartItem = cartItemDao.getManyCartItemByIdCart(cart.getId());
        cart.setListCartItem(listCartItem);
        if (listCartItem.isEmpty()) {
            System.out.println("gio hang trong");
            cart.setNotes("empty");
            cart.setStatus("inactive");
            cart.setTotalPrice("0");
            cart.setCodeDiscount("0");
            cartDao.updateCartAfterOrder(cart);
        } else if ((cart.getStatus().equalsIgnoreCase("inactive") && !listCartItem.isEmpty())) {
            updateOneCartWithoutTotalPrice(cart);
            updateTotalPrice(listCartItem, cart, idCustomer);
        } else if (cart.getStatus().equalsIgnoreCase("active")) {
            updateTotalPrice(listCartItem, cart, idCustomer);
        }
    }

    @Transactional
    @Override
    public CartDTO updateCartAfterOrder() {
        CartDTO cartDTO = getOneCartByIdCustomer();
        cartDTO.setNotes("empty");
        cartDTO.setStatus("inactive");
        cartDTO.setTotalPrice("0");
        cartDTO.setCodeDiscount("0");
        cartDao.updateCartAfterOrder(cartDTO);
        List<Long> ids = cartDTO.getListCartItem().stream().map(CartItemDTO::getId).collect(Collectors.toList());
        cartItemDao.deleteManyCartItem(ids);
        return cartDTO;
    }

    public void updateTotalPrice(List<CartItemDTO> listCartItem, CartDTO cart, Long idCustomer) {
        Long tempPrice = 0L;
        for (CartItemDTO cartItem : listCartItem) {
            tempPrice += Long.parseLong(cartItem.getPrice());
        }

        // TODO tim hieu 1 code tuong ung voi phan tram
        String codeDiscount = cart.getCodeDiscount();
        Long codeDiscountToPercent = Long.parseLong(codeDiscount);

        Double totalPrice = tempPrice - ((Double.valueOf(codeDiscountToPercent) / 100) * tempPrice);

        cartDao.updateTotalPrice(String.valueOf(totalPrice), idCustomer);
        entityManager.clear();
    }

}
