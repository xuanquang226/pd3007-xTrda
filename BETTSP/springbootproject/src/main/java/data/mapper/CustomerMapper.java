package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.CustomerDTO;
import data.entities.CustomerEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "listOrder", ignore = true)
    CustomerDTO toDto(CustomerEntity entity);

    @Mapping(target = "listOrder", ignore = true)
    List<CustomerDTO> toDto(List<CustomerEntity> entities);

    CustomerEntity toEntity(CustomerDTO dto);

    List<CustomerEntity> toEntity(List<CustomerDTO> dtos);
}
