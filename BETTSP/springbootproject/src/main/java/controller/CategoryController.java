package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import data.dto.CategoryDTO;
import services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/one/{id}")
    public CategoryDTO getOneCategory(@PathVariable Long id) {
        return categoryService.getOneCategory(id);
    }

    @GetMapping("/many")
    public List<CategoryDTO> getManyCategory() {
        return categoryService.getAllCategory();
    }

    @PostMapping("")
    public void createOneCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createOneCategory(categoryDTO);
    }

    @PutMapping("")
    public void updateOneCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateOneCategory(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOneCategory(@PathVariable Long id) {
        categoryService.deleteOneCategory(id);
    }

    @DeleteMapping("/many")
    public void deleteManyCategory(@RequestBody List<Long> listId) {
        categoryService.deleteManyCategory(listId);
    }
}
