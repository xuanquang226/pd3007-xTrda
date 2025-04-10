package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import data.dto.OrderDTO;
import data.entities.OrderEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderEntity toEntity(OrderDTO dto);

    List<OrderEntity> toEntity(List<OrderDTO> dtos);

    @Mapping(target = "listOrderLine", ignore = true)
    OrderDTO toDto(OrderEntity entity);

    @Mapping(target = "listOrderLine", ignore = true)
    List<OrderDTO> toDto(List<OrderEntity> entities);
}
