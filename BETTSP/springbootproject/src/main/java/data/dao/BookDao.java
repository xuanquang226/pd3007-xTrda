package data.dao;

import java.util.List;

import data.dto.BookDTO;

public interface BookDao {
    BookDTO getOneBook(Long id);

    List<BookDTO> getAll();

    BookDTO createOneBook(BookDTO book);

    void deleteOneBook(Long id);

    BookDTO updateOne(BookDTO book);
}
