package services;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import data.dto.ProductDTO;

public interface ProductService {
    List<ProductDTO> getAllProduct();

    ProductDTO getOneProduct(Long id, String categoryType);

    List<ProductDTO> findManyProductByIds(List<Long> idProductList);

    void createOneProduct(ProductDTO productDTO, MultipartFile[] files);

    void updateOneProduct(ProductDTO productDTO);

    void deleteOneProduct(Long id);

    void deleteManyProduct(List<Long> listId);

    void updateQuantityAfterOrder(HashMap<Long, Long> productIdToQuantityMap);

    ProductDTO getOneProduct(Long id);
}
