package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.RoleAccountDTO;
import data.entities.RoleAccountEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface RoleAccountMapper {
    RoleAccountMapper INSTANCE = Mappers.getMapper(RoleAccountMapper.class);

    RoleAccountDTO toDto(RoleAccountEntity entity);

    List<RoleAccountDTO> toDto(List<RoleAccountEntity> entities);

    RoleAccountEntity toEntity(RoleAccountDTO dto);

    List<RoleAccountEntity> toEntity(List<RoleAccountEntity> entities);
}
