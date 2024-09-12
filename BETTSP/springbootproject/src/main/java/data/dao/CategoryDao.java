package data.dao;

import java.util.List;

import data.dto.CategoryDTO;

public interface CategoryDao {
    List<CategoryDTO> getAllCategory();

    CategoryDTO getOneCategory(Long id);

    void createOneCategory(CategoryDTO categoryDTO);

    void updateOneCategory(CategoryDTO categoryDTO);

    void deleteOneCategory(Long id);

    void deleteManyCategory(List<Long> listId);
}
