package data.mapper;

import data.dto.ImageDTO;
import data.entities.ImageEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-12T17:02:49+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageEntity toEntity(ImageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ImageEntity imageEntity = new ImageEntity();

        if ( dto.getDescription() != null ) {
            imageEntity.setDescription( dto.getDescription() );
        }
        if ( dto.getId() != null ) {
            imageEntity.setId( dto.getId() );
        }
        if ( dto.getIdProduct() != null ) {
            imageEntity.setIdProduct( dto.getIdProduct() );
        }
        if ( dto.getUrl() != null ) {
            imageEntity.setUrl( dto.getUrl() );
        }

        return imageEntity;
    }

    @Override
    public List<ImageEntity> toEntity(List<ImageDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ImageEntity> list = new ArrayList<ImageEntity>( dtos.size() );
        for ( ImageDTO imageDTO : dtos ) {
            list.add( toEntity( imageDTO ) );
        }

        return list;
    }

    @Override
    public ImageDTO toDto(ImageEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ImageDTO imageDTO = new ImageDTO();

        if ( entity.getDescription() != null ) {
            imageDTO.setDescription( entity.getDescription() );
        }
        if ( entity.getId() != null ) {
            imageDTO.setId( entity.getId() );
        }
        if ( entity.getIdProduct() != null ) {
            imageDTO.setIdProduct( entity.getIdProduct() );
        }
        if ( entity.getUrl() != null ) {
            imageDTO.setUrl( entity.getUrl() );
        }

        return imageDTO;
    }

    @Override
    public List<ImageDTO> toDto(List<ImageEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ImageDTO> list = new ArrayList<ImageDTO>( entities.size() );
        for ( ImageEntity imageEntity : entities ) {
            list.add( toDto( imageEntity ) );
        }

        return list;
    }
}
