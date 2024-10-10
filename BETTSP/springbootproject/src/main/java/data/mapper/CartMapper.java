package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.CartDTO;
import data.entities.CartEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartEntity toEntity(CartDTO dto);

    List<CartEntity> toEntity(List<CartDTO> dtos);

    @Mapping(target = "listCartItem", ignore = true)
    CartDTO toDto(CartEntity entity);

    @Mapping(target = "listCartItem", ignore = true)
    List<CartDTO> toDto(List<CartEntity> entities);
}
