package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.ProductDTO;

public interface ProductDao {
    List<ProductDTO> getAllProduct();

    ProductDTO getOneProduct(Long id);

    List<ProductDTO> findListProductByName(String name);

    void createOneProduct(ProductDTO productDTO);

    void updateOneProduct(ProductDTO productDTO);

    void deleteOneProduct(Long id);

    void deleteManyProduct(List<Long> listId);

    List<ProductDTO> findProductByIdCategory(Long id);

    List<ProductDTO> findProductByIds(List<Long> productIdList);

    void updateQuantityAfterOrder(List<ProductDTO> productDTOs);

    ProductDTO getProductLatest(String name);
}
