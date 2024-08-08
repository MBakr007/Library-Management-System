package org.example.librarymanagementsystem.service;


import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.example.librarymanagementsystem.exception.BookException;
import org.example.librarymanagementsystem.exception.PatronException;
import org.example.librarymanagementsystem.model.Book;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new BookException("this book is not found."));
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return Optional.ofNullable(bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setIsbn(updatedBook.getIsbn());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new BookException("this book is not found.")));
    }
    public boolean deleteBook(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return true;
                })
                .orElseThrow(() -> new BookException("this book is not found."));
    }
}
