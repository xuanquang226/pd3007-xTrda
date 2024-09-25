package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.MailDTO;
import data.entities.MailEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, componentModel = "spring")
public interface MailMapper {
    MailMapper INSTANCE = Mappers.getMapper(MailMapper.class);

    MailDTO toDto(MailEntity entity);

    List<MailDTO> toDto(List<MailEntity> entities);

    MailEntity toEntity(MailDTO dto);

    List<MailEntity> toEntity(List<MailDTO> dto);
}
