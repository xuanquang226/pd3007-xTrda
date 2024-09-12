package data.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.BookDao;
import data.dto.BookDTO;
import data.mapper.BookMapper;
import data.repositories.BookRepository;

@Service
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository repository;

    @Autowired
    private BookMapper mapper;

    @Override
    public BookDTO getOneBook(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new NullPointerException("Loi khong ton tai")));
    }

    @Override
    public List<BookDTO> getAll() {
        return mapper.toDto(repository.findAll());
    }

    @Override
    public BookDTO createOneBook(BookDTO book) {
        return mapper.toDto(repository.save(mapper.toEntity(book)));
    }

    @Override
    public void deleteOneBook(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BookDTO updateOne(BookDTO book) {
        // TODO Auto-generated method stub
        return mapper.toDto(repository.save(mapper.toEntity(book)));
    }

}
