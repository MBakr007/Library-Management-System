package org.example.librarymanagementsystem.service;


import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.example.librarymanagementsystem.model.Book;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BookService {

    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void saveFakeBooks(){
        Faker faker = new Faker();

        List<Book> books = IntStream.range(0, 10) // generate 10 books
                .mapToObj(i -> new Book(
                        faker.book().title(),
                        faker.book().author(),
                        String.valueOf(faker.number().numberBetween(1900, 2023)),
                        String.valueOf(faker.number().digits(13))
                ))
                .toList();

        books.forEach(this::saveBook);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
