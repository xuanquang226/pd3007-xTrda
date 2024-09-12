package data.dao.impl;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CategoryDao;
import data.dto.CategoryDTO;
import data.mapper.CategoryMapper;
import data.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper mapper;

    @Override
    public List<CategoryDTO> getAllCategory() {
        return mapper.toDto(repository.findAll());
    }

    @Override
    public CategoryDTO getOneCategory(Long id) {
        return mapper
                .toDto(repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy entity kiểm tra lại id")));
    }

    @Override
    public void createOneCategory(CategoryDTO categoryDTO) {
        System.out.println(categoryDTO.getName());
        repository.save(mapper.toEntity(categoryDTO));
    }

    @Override
    public void updateOneCategory(CategoryDTO categoryDTO) {

        Optional<CategoryDTO> optional = Optional.ofNullable(getOneCategory(categoryDTO.getId()));
        if (optional.isPresent()) {
            repository.save(mapper.toEntity(categoryDTO));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity cu");
        }
    }

    @Override
    public void deleteOneCategory(Long id) {
        Optional<CategoryDTO> optional = Optional.ofNullable(getOneCategory(id));
        if (optional.isPresent()) {
            repository.delete(mapper.toEntity(getOneCategory(id)));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity");
        }
    }

    @Override
    public void deleteManyCategory(List<Long> listId) {
        for (Long id : listId) {
            try {
                Optional<CategoryDTO> optional = Optional.ofNullable(getOneCategory(id));
                if (optional.isPresent()) {
                    repository.delete(mapper.toEntity(getOneCategory(id)));
                } else {
                    throw new EntityNotFoundException("Khong tim thay entity cu");
                }
            } catch (EntityNotFoundException entityNotFoundException) {
                System.getLogger(CategoryDaoImpl.class.getName()).log(Level.INFO, entityNotFoundException);
            }
        }
    }
}
