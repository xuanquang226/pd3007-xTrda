package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dto.BookDTO;
import services.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    // @Autowired
    // private KafkaTemplate kafkaTemplate;
    @Autowired
    private BookService services;

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return services.getOneBook(id);
    }

    @GetMapping
    public List<BookDTO> getAll() {
        return services.getAll();
    }

    @PostMapping
    public BookDTO createOneBook(@RequestBody BookDTO book) {
        // kafkaTemplate.send("notification", book);
        return services.createOneBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteOneBook(@PathVariable Long id) {
        services.deleteOne(id);
    }

    @PutMapping()
    public BookDTO updateOneBook(@RequestBody BookDTO book) {
        return services.updateOne(book);
    }
}
