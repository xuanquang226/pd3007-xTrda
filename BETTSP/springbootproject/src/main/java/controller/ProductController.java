package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dto.ProductDTO;
import services.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public void createOneProduct(@RequestBody ProductDTO productDTO) {
        productService.createOneProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOneProduct(@PathVariable Long id) {
        productService.deleteOneProduct(id);
    }

    @GetMapping("/{id}")
    public ProductDTO getOneProduct(@PathVariable Long id) {
        return productService.getOneProduct(id);
    }
}
