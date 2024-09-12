package data.mapper;

import data.dto.BookDTO;
import data.entities.BookEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-12T17:02:50+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO toDto(BookEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        if ( entity.getAuthor() != null ) {
            bookDTO.setAuthor( entity.getAuthor() );
        }
        if ( entity.getId() != null ) {
            bookDTO.setId( entity.getId() );
        }
        if ( entity.getName() != null ) {
            bookDTO.setName( entity.getName() );
        }

        return bookDTO;
    }

    @Override
    public List<BookDTO> toDto(List<BookEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<BookDTO> list = new ArrayList<BookDTO>( entities.size() );
        for ( BookEntity bookEntity : entities ) {
            list.add( toDto( bookEntity ) );
        }

        return list;
    }

    @Override
    public BookEntity toEntity(BookDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        if ( dto.getAuthor() != null ) {
            bookEntity.setAuthor( dto.getAuthor() );
        }
        if ( dto.getId() != null ) {
            bookEntity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            bookEntity.setName( dto.getName() );
        }

        return bookEntity;
    }

    @Override
    public List<BookEntity> toEntity(List<BookDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<BookEntity> list = new ArrayList<BookEntity>( dtos.size() );
        for ( BookDTO bookDTO : dtos ) {
            list.add( toEntity( bookDTO ) );
        }

        return list;
    }
}
