package data.dao.impl;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CartItemDao;
import data.dto.CartItemDTO;
import data.mapper.CartItemMapper;
import data.repositories.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CartItemDaoImpl implements CartItemDao {

    @Autowired
    private CartItemRepository repository;

    @Autowired
    private CartItemMapper mapper;

    @Override
    public void createManyCartItem(List<CartItemDTO> dtos) {
        dtos.forEach(dto -> {
            repository.save(mapper.toEntity(dto));
        });
    }

    @Override
    public void createOneCartItem(CartItemDTO dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public void deleteManyCartItem(List<Long> ids) {
        for (Long id : ids) {
            try {
                Optional<CartItemDTO> optional = Optional.ofNullable(getOneCartItem(id));
                if (optional.isPresent()) {
                    repository.deleteById(id);
                } else {
                    throw new EntityNotFoundException("Khong tim thay entity voi id = ");
                }
            } catch (EntityNotFoundException entityNotFoundException) {
                System.getLogger(CartItemDaoImpl.class.getName()).log(Level.INFO, entityNotFoundException);
            }
        }

    }

    @Override
    public void deleteOneCartItem(Long id) {
        Optional<CartItemDTO> optional = Optional.ofNullable(getOneCartItem(id));
        if (optional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Khong tim thay entity voi id = ");
        }

    }

    @Override
    public List<CartItemDTO> getAllCartItem() {
        return mapper.toDto(repository.findAll());
    }

    @Override
    public CartItemDTO getOneCartItem(Long id) throws EntityNotFoundException {
        return mapper
                .toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity")));
    }

    @Override
    public void updateManyCartItem(List<CartItemDTO> dtos) {
        for (CartItemDTO dto : dtos) {
            try {
                Optional<CartItemDTO> optional = Optional.ofNullable(getOneCartItem(dto.getId()));
                if (optional.isPresent()) {
                    repository.save(mapper.toEntity(dto));
                } else {
                    throw new EntityNotFoundException("Khong tim thay entity voi id = ");
                }
            } catch (EntityNotFoundException entityNotFoundException) {
                System.getLogger(CartItemDaoImpl.class.getName()).log(Level.INFO, entityNotFoundException);
            }
        }
    }

    @Override
    public void updateOneCartItem(CartItemDTO dto) {
        Optional<CartItemDTO> optional = Optional.ofNullable(getOneCartItem(dto.getId()));
        if (optional.isPresent()) {
            repository.save(mapper.toEntity(dto));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity voi id = ");
        }
    }

}
