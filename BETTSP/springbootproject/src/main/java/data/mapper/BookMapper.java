package data.mapper;

import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import data.dto.BookDTO;
import data.entities.BookEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring"
        )
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
 
    BookDTO toDto(BookEntity entity);
    List<BookDTO> toDto(List<BookEntity> entities);

    BookEntity toEntity(BookDTO dto);
    List<BookEntity> toEntity(List<BookDTO> dtos);
}
