package data.dao;

import java.util.List;

import data.dto.ProductDTO;

public interface ProductDao {
    List<ProductDTO> getAllProduct();

    ProductDTO getOneProduct(Long id);

    List<ProductDTO> findOneProductByName(String name);

    void createOneProduct(ProductDTO productDTO);

    void updateOneProduct(ProductDTO productDTO);

    void deleteOneProduct(Long id);

    void deleteManyProduct(List<Long> listId);

    List<ProductDTO> findProductByIdCategory(Long id);
}
