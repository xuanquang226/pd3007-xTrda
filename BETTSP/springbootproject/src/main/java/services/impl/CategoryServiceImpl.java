package services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CategoryDao;
import data.dao.ImageDao;
import data.dao.ProductDao;
import data.dto.CategoryDTO;
import data.dto.ProductDTO;
import services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDTO> getAllCategory() {
        return categoryDao.getAllCategory();
    }

    @Override
    public CategoryDTO getOneCategory(Long id) {
        List<ProductDTO> productDTOs = productDao.findProductByIdCategory(id);
        List<ProductDTO> productDTOsWithImgDtos = new ArrayList<>();
        for (ProductDTO productDTO : productDTOs) {
            productDTO.setImageDTOs(imageDao.findImageDTOsByIdProduct(productDTO.getId()));
            productDTOsWithImgDtos.add(productDTO);
        }
        CategoryDTO categoryDTO = categoryDao.getOneCategory(id);
        categoryDTO.setProductDTOs(productDTOsWithImgDtos);
        return categoryDTO;
    }

    @Override
    public void createOneCategory(CategoryDTO categoryDTO) {
        categoryDao.createOneCategory(categoryDTO);
    }

    @Override
    public void updateOneCategory(CategoryDTO categoryDTO) {
        categoryDao.updateOneCategory(categoryDTO);
    }

    @Override
    public void deleteOneCategory(Long id) {
        categoryDao.deleteOneCategory(id);
    }

    @Override
    public void deleteManyCategory(List<Long> listId) {
        categoryDao.deleteManyCategory(listId);
    }

}