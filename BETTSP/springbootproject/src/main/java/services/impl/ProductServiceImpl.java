package services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import data.dao.CategoryDao;
import data.dao.ImageDao;
import data.dao.ProductDao;
import data.dto.CategoryDTO;
import data.dto.ImageDTO;
import data.dto.ProductDTO;
import services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private CategoryDao categoryDao;

    @Value("${HOST}")
    private String host;

    @Override
    public void createOneProduct(ProductDTO productDTO, MultipartFile[] files) {
        // String directory =
        // "D:/Quang/project-30-07-2024/BETTSP/springbootproject/src/main/resources/static/images-storage/";
        String directory = "/src/app/src/main/resources/static/images-storage/";
        for (MultipartFile file : files) {
            Path filePath = Paths.get(directory, file.getOriginalFilename());
            try {
                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productDao.createOneProduct(productDTO);
        ProductDTO latestProductDTO = productDao.getProductLatest(productDTO.getName());

        List<ImageDTO> imageDTOs = new ArrayList<>();
        String urlResource = "https://" + host + "/images-storage/";
        for (ImageDTO imageDTO : productDTO.getImageDTOs()) {
            String imgUrl = urlResource + imageDTO.getDescription();
            imageDTO.setIdProduct(latestProductDTO.getId());
            imageDTO.setUrl(imgUrl);
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
    public ProductDTO getOneProduct(Long id, String categoryType) {
        List<ImageDTO> imageDTOs = imageDao.findImageDTOsByIdProduct(id);
        ProductDTO productDTO = productDao.getOneProduct(id);
        productDTO.setImageDTOs(imageDTOs);

        // Kiem tra neu dung ten category moi tra ve product duoc lay tu id tren. Con
        // nhap url tam bay thi khong cho :D
        CategoryDTO categoryDTO = categoryDao.getOneCategory(productDTO.getIdCategory());
        if (categoryDTO.getName().equalsIgnoreCase(categoryType)) {
            return productDTO;
        } else {
            return null;
        }
    }

    @Override
    public void updateOneProduct(ProductDTO productDTO) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateQuantityAfterOrder(HashMap<Long, Long> productIdToQuantityMap) {
        List<Long> productIdList = new ArrayList<>();
        productIdToQuantityMap.forEach((k, v) -> productIdList.add(k));

        List<ProductDTO> productDTOList = productDao.findProductByIds(productIdList);

        for (ProductDTO product : productDTOList) {
            if (productIdToQuantityMap.containsKey(product.getId())) {
                Long updatedQuantity = product.getQuantity() - productIdToQuantityMap.get(product.getId());
                product.setQuantity(updatedQuantity);
            }
        }
        productDao.updateQuantityAfterOrder(productDTOList);
    }

    @Override
    public List<ProductDTO> findManyProductByIds(List<Long> idProductList) {
        List<ProductDTO> productDtoList = productDao.findProductByIds(idProductList);
        Map<Long, List<ImageDTO>> imageDTOMap = new HashMap<>();
        for (Long idProduct : idProductList) {
            imageDTOMap.put(idProduct, imageDao.findImageDTOsByIdProduct(idProduct));
        }

        productDtoList.forEach(product -> {
            if (imageDTOMap.containsKey(product.getId())) {
                product.setImageDTOs(imageDTOMap.get(product.getId()));
            }
        });

        return productDtoList;
    }

    @Override
    public ProductDTO getOneProduct(Long id) {
        List<ImageDTO> imageDTOs = imageDao.findImageDTOsByIdProduct(id);
        ProductDTO productDTO = productDao.getOneProduct(id);
        productDTO.setImageDTOs(imageDTOs);
        return productDTO;
    }

}
