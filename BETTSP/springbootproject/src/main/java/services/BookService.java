package services;

import java.util.List;

import data.dto.BookDTO;

public interface BookService {
    BookDTO getOneBook(Long id);

    List<BookDTO> getAll();

    BookDTO createOneBook(BookDTO book);

    void deleteOne(Long id);

    BookDTO updateOne(BookDTO book);
}
