package services.impl;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.dao.AccountDao;
import data.dao.CartDao;
import data.dao.CartItemDao;
import data.dao.ProductDao;
import data.dto.AccountDTO;
import data.dto.CartItemDTO;
import data.dto.ProductDTO;
import jakarta.persistence.EntityNotFoundException;
import services.CartItemService;
import utils.objects.AccountAuth;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private AccountAuth accountAuth;

    @Override
    public void createManyCartItem(List<CartItemDTO> dtos) {
        List<CartItemDTO> newDtos = new ArrayList<>();
        for (CartItemDTO dto : dtos) {
            try {
                if (dto.getIdProduct() != null) {
                    newDtos.add(dto);
                } else {
                    throw new IllegalArgumentException("CartItemDto can't be null");
                }
            } catch (IllegalArgumentException eArgumentException) {
                System.getLogger(CartItemServiceImpl.class.getName()).log(Level.INFO, eArgumentException);
            }
        }
        cartItemDao.createManyCartItem(newDtos);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void createOneCartItem(CartItemDTO dto) {
        // Inserting the name of product and price after adding it to the cart
        // Validate the value before creating the cart item

        dto.setIdCart(cartDao.getOneCartByIdCustomer(accountAuth.getAccount().getIdCustomer()).getId());
        CartItemDTO cartItemDTO = filterDuplicateProduct(dto);
        ProductDTO productDTO = null;
        try {
            productDTO = productDao.getOneProduct(dto.getIdProduct());
            cartItemDTO.setName(productDTO.getName());
            String price = (Long.parseLong(productDTO.getPrice()) * dto.getQuantity()) + "";
            cartItemDTO.setPrice(price);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw new IllegalArgumentException();
        }

        if (cartItemDTO.getQuantity() > productDTO.getQuantity()) {
            throw new IllegalArgumentException();
        }

        if (cartItemDTO.getIdProduct() != null) {
            cartItemDao.createOneCartItem(cartItemDTO);
        } else {
            throw new IllegalArgumentException("CartItemDto can't be null");
        }
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
    public void updateOneCartItem(CartItemDTO dto) throws IllegalArgumentException, EntityNotFoundException {

        CartItemDTO newDto = cartItemDao.getOneCartItemByIdProductAndIdCart(dto.getIdProduct(), dto.getIdCart());
        ProductDTO productDTO = productDao.getOneProduct(newDto.getIdProduct());

        if (dto.getQuantity() != null) {
            if (dto.getQuantity() > productDTO.getQuantity()) {
                throw new IllegalArgumentException();
            }
            if (dto.getQuantity() == 0) {
                deleteOneCartItem(newDto.getId());
                return;
            }
            newDto.setQuantity(dto.getQuantity());
            String newPrice = Long.parseLong(productDTO.getPrice().toString()) * dto.getQuantity() + "";
            newDto.setPrice(newPrice);
        }
        if (dto.getNote() != null) {
            newDto.setNote(dto.getNote());
        }
        cartItemDao.updateOneCartItem(newDto);
    }

    @Override
    public List<CartItemDTO> getAllCartItemByIdCart(Long idCart) {
        return cartItemDao.getManyCartItemByIdCart(idCart);
    }

    @Override
    public CartItemDTO filterDuplicateProduct(CartItemDTO cartItem) {
        try {
            Optional<CartItemDTO> cartItemByIdCartAndIdProduct = Optional.ofNullable(
                    cartItemDao.getOneCartItemByIdProductAndIdCart(cartItem.getIdProduct(), cartItem.getIdCart()));
            if (cartItemByIdCartAndIdProduct.isPresent()) {
                deleteOneCartItem(cartItemByIdCartAndIdProduct.get().getId());
                Long newQuantity = cartItem.getQuantity() + cartItemByIdCartAndIdProduct.get().getQuantity();
                cartItem.setQuantity(newQuantity);
                return cartItem;
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException exception) {
            return cartItem;
        }

    }
}
