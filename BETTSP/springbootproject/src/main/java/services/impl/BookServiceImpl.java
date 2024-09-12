package services.impl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.dao.BookDao;
import data.dto.BookDTO;

import services.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public BookDTO getOneBook(Long id) throws NullPointerException {
        return bookDao.getOneBook(id);
    }

    @Override
    public List<BookDTO> getAll() {
        return bookDao.getAll();
    }

    @Override
    public BookDTO createOneBook(BookDTO book) {
        return bookDao.createOneBook(book);
    }

    @Override
    public void deleteOne(Long id) {
        bookDao.deleteOneBook(id);
    }

    @Override
    public BookDTO updateOne(BookDTO book) {
        if (ObjectUtils.isEmpty(getOneBook(book.getId()))) {
            throw new NullPointerException();
        }
        return bookDao.updateOne(book);
    }

}
