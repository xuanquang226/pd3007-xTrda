package services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.ImageDao;
import data.dao.ProductDao;
import data.dto.ImageDTO;
import data.dto.ProductDTO;
import services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ImageDao imageDao;

    @Override
    public void createOneProduct(ProductDTO productDTO) {
        // productDao.createOneProduct(productDTO);
        // ProductDTO newProduct =
        // productDao.findOneProductByName(productDTO.getName());
        // List<ImageDTO> imageDTOs = new ArrayList<>();
        // for (String url : productDTO.getUrlImages()) {
        // ImageDTO imageDTO = new ImageDTO();
        // imageDTO.setIdProduct(newProduct.getId());
        // imageDTO.setUrl(url);
        // imageDTOs.add(imageDTO);
        // }
        // imageDao.createManyImage(imageDTOs);

        productDao.createOneProduct(productDTO);
        ProductDTO newProductDTO = productDao.findOneProductByName(productDTO.getName());
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (ImageDTO imageDTO : productDTO.getImageDTOs()) {
            imageDTO.setIdProduct(newProductDTO.getId());
            imageDTOs.add(imageDTO);
        }
        imageDao.createManyImage(imageDTOs);
    }

    @Override
    public void deleteManyProduct(List<Long> listId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteOneProduct(Long id) {
        imageDao.deleteImagesByIdProduct(id);
        productDao.deleteOneProduct(id);
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductDTO getOneProduct(Long id) {
        List<ImageDTO> imageDTOs = imageDao.findImageDTOsByIdProduct(id);
        ProductDTO productDTO = productDao.getOneProduct(id);
        productDTO.setImageDTOs(imageDTOs);
        return productDTO;
    }

    @Override
    public void updateOneProduct(ProductDTO productDTO) {
        // TODO Auto-generated method stub

    }

}
