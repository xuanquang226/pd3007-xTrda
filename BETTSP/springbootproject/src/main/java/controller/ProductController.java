package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.dto.ProductDTO;
import services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin")
    public void createOneProduct(@RequestPart("product") String productJson,
            @RequestPart("files") MultipartFile[] files) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);
        productService.createOneProduct(productDTO, files);
    }

    @DeleteMapping("/{id}")
    public void deleteOneProduct(@PathVariable Long id) {
        productService.deleteOneProduct(id);
    }

    @GetMapping("/{id}")
    public ProductDTO getOneProduct(@PathVariable Long id, @RequestParam String categoryType) {
        return productService.getOneProduct(id, categoryType);
    }

    @PostMapping("/many")
    public ResponseEntity<List<ProductDTO>> findManyProductByIds(@RequestBody List<Long> idProductList) {
        return ResponseEntity.ok(productService.findManyProductByIds(idProductList));
    }

    @GetMapping("/2/{id}")
    public ResponseEntity<ProductDTO> getOneProduct2(@PathVariable Long id) {
        ProductDTO productDTO = productService.getOneProduct(id);
        if (productDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(productDTO);
    }
}
