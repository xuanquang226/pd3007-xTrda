package data.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.dao.CartDao;
import data.dto.CartDTO;
import data.mapper.CartMapper;
import data.repositories.CartRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CartDaoImpl implements CartDao {

    @Autowired
    private CartMapper mapper;

    @Autowired
    private CartRepository repository;

    @Override
    public CartDTO getOneCartByIdCart(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Transactional
    @Override
    public CartDTO getOneCartByIdCustomer(Long idCustomer) {
        return mapper.toDto(repository.findByIdCustomer(idCustomer).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public void updateOneCartWithoutTotalPrice(CartDTO dto) {
        Optional<CartDTO> optional = Optional.ofNullable((getOneCartByIdCart(dto.getId())));
        if (optional.isPresent()) {
            repository.save(mapper.toEntity(dto));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity de update");
        }
    }

    @Override
    public void createOneCart(CartDTO dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Transactional
    @Override
    public void updateTotalPrice(String totalPrice, Long idCustomer) {
        repository.updateTotalPriceByIdCustomerAndTotalPrice(totalPrice, idCustomer);
    }

}
