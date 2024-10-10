package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.OrderLineDTO;
import data.entities.OrderLineEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface OrderLineMapper {
    OrderLineMapper INSTANCE = Mappers.getMapper(OrderLineMapper.class);

    OrderLineEntity toEntity(OrderLineDTO dto);

    List<OrderLineEntity> toEntity(List<OrderLineDTO> dtos);

    OrderLineDTO toDto(OrderLineEntity entity);

    List<OrderLineDTO> toDto(List<OrderLineEntity> entities);
}
