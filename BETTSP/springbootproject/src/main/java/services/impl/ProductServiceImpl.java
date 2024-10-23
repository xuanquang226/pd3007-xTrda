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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void createOneProduct(ProductDTO productDTO, MultipartFile[] files) {
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
        /*
         * - gui json co chua cac thong tin cua productdto, product dto co gi? co name
         * des idcategory va mot list image
         * - list image xu ly sao? cung la mot doi tuong luon chua name des va (link img
         * se null va duoc them vao sau)
         * -
         */
        String directory = "D:/Quang/project-30-07-2024/BETTSP/springbootproject/src/main/resources/static/images-storage/";
        for (MultipartFile file : files) {
            Path filePath = Paths.get(directory, file.getOriginalFilename());
            try {
                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productDao.createOneProduct(productDTO);
        List<ProductDTO> listProductDTOByName = productDao.findOneProductByName(productDTO.getName());
        listProductDTOByName.sort(Comparator.comparingLong((product) -> product.getId()));

        // Dat con tro o phan tu cuoi cung lay idproduct thuc su roi set cho image tao
        // lien ket
        ListIterator<ProductDTO> iListIterator = listProductDTOByName.listIterator(listProductDTOByName.size());
        ProductDTO latestProductDTO = new ProductDTO();
        if (iListIterator.hasPrevious()) {
            latestProductDTO = iListIterator.previous();
            List<ImageDTO> imageDTOs = new ArrayList<>();
            String urlResource = "http://localhost:8082/images-storage/";
            for (ImageDTO imageDTO : productDTO.getImageDTOs()) {
                String imgUrl = urlResource + imageDTO.getDescription();
                imageDTO.setIdProduct(latestProductDTO.getId());
                imageDTO.setUrl(imgUrl);
                imageDTOs.add(imageDTO);
            }
            imageDao.createManyImage(imageDTOs);
        }

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
}
