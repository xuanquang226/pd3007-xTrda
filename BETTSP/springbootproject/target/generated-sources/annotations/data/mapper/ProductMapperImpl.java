package data.mapper;

import data.dto.ProductDTO;
import data.entities.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-13T11:29:33+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity toEntity(ProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        if ( dto.getDescription() != null ) {
            productEntity.setDescription( dto.getDescription() );
        }
        if ( dto.getId() != null ) {
            productEntity.setId( dto.getId() );
        }
        if ( dto.getIdCategory() != null ) {
            productEntity.setIdCategory( dto.getIdCategory() );
        }
        if ( dto.getName() != null ) {
            productEntity.setName( dto.getName() );
        }

        return productEntity;
    }

    @Override
    public List<ProductEntity> toEntity(List<ProductDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ProductEntity> list = new ArrayList<ProductEntity>( dtos.size() );
        for ( ProductDTO productDTO : dtos ) {
            list.add( toEntity( productDTO ) );
        }

        return list;
    }

    @Override
    public ProductDTO toDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        if ( entity.getDescription() != null ) {
            productDTO.setDescription( entity.getDescription() );
        }
        if ( entity.getId() != null ) {
            productDTO.setId( entity.getId() );
        }
        if ( entity.getIdCategory() != null ) {
            productDTO.setIdCategory( entity.getIdCategory() );
        }
        if ( entity.getName() != null ) {
            productDTO.setName( entity.getName() );
        }

        return productDTO;
    }

    @Override
    public List<ProductDTO> toDto(List<ProductEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( entities.size() );
        for ( ProductEntity productEntity : entities ) {
            list.add( toDto( productEntity ) );
        }

        return list;
    }
}
