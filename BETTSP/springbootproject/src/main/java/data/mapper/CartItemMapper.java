package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.CartItemDTO;
import data.entities.CartItemEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    CartItemEntity toEntity(CartItemDTO dto);

    List<CartItemEntity> toEntity(List<CartItemDTO> dtos);

    CartItemDTO toDto(CartItemEntity entity);

    List<CartItemDTO> toDto(List<CartItemEntity> entities);
}
