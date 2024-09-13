package data.mapper;

import data.dto.CategoryDTO;
import data.entities.CategoryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-13T11:29:33+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO toDto(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        if ( entity.getId() != null ) {
            categoryDTO.setId( entity.getId() );
        }
        if ( entity.getName() != null ) {
            categoryDTO.setName( entity.getName() );
        }

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> toDto(List<CategoryEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( entities.size() );
        for ( CategoryEntity categoryEntity : entities ) {
            list.add( toDto( categoryEntity ) );
        }

        return list;
    }

    @Override
    public CategoryEntity toEntity(CategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        if ( dto.getId() != null ) {
            categoryEntity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            categoryEntity.setName( dto.getName() );
        }

        return categoryEntity;
    }

    @Override
    public List<CategoryEntity> toEntity(List<CategoryDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<CategoryEntity> list = new ArrayList<CategoryEntity>( dtos.size() );
        for ( CategoryDTO categoryDTO : dtos ) {
            list.add( toEntity( categoryDTO ) );
        }

        return list;
    }
}
