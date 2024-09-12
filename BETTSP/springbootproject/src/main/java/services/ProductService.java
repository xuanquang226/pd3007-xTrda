package services;

import java.util.List;

import data.dto.ProductDTO;

public interface ProductService {
    List<ProductDTO> getAllProduct();

    ProductDTO getOneProduct(Long id);

    void createOneProduct(ProductDTO productDTO);

    void updateOneProduct(ProductDTO productDTO);

    void deleteOneProduct(Long id);

    void deleteManyProduct(List<Long> listId);
}
