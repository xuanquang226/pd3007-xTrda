package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.CategoryDTO;
import data.entities.CategoryEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "productDTOs", ignore = true)
    CategoryDTO toDto(CategoryEntity entity);

    @Mapping(target = "productDTOs", ignore = true)
    List<CategoryDTO> toDto(List<CategoryEntity> entities);

    CategoryEntity toEntity(CategoryDTO dto);

    List<CategoryEntity> toEntity(List<CategoryDTO> dtos);
}
