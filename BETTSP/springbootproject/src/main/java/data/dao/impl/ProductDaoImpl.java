package data.dao.impl;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.ProductDao;
import data.dto.ProductDTO;
import data.mapper.ProductMapper;
import data.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void createOneProduct(ProductDTO productDTO) {
        productRepository.save(productMapper.toEntity(productDTO));
    }

    @Override
    public void deleteManyProduct(List<Long> listId) {
        for (Long id : listId) {
            try {
                Optional<ProductDTO> optional = Optional.ofNullable(getOneProduct(id));
                if (optional.isPresent()) {
                    productRepository.delete(productMapper.toEntity(optional.get()));
                } else {
                    throw new EntityNotFoundException("Khong tim thay entity");
                }
            } catch (EntityNotFoundException entityNotFoundException) {
                System.getLogger(ProductDaoImpl.class.getName()).log(Level.WARNING, entityNotFoundException);
            }
        }

    }

    @Override
    public void deleteOneProduct(Long id) {
        Optional<ProductDTO> optional = Optional.ofNullable(getOneProduct(id));
        if (optional.isPresent()) {
            productRepository.delete(productMapper.toEntity(optional.get()));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity");
        }

    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    public ProductDTO getOneProduct(Long id) {
        return productMapper.toDto(
                productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity")));
    }

    @Override
    public void updateOneProduct(ProductDTO productDTO) {
        Optional<ProductDTO> optional = Optional.ofNullable(getOneProduct(productDTO.getId()));
        if (optional.isPresent()) {
            productRepository.save(productMapper.toEntity(optional.get()));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity");
        }
    }

    @Override
    public ProductDTO findOneProductByName(String name) {
        return productMapper.toDto(productRepository.findByName(name));
    }

    @Override
    public List<ProductDTO> findProductByIdCategory(Long id) {
        return productMapper.toDto(productRepository.findProductsByIdCategory(id));
    }

}
