package org.example.librarymanagementsystem.controller;

import org.example.librarymanagementsystem.model.Book;
import org.example.librarymanagementsystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("Title", "Author", "2023", "1234567890123");
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    void testGetBookById() {
        when(bookService.getBookById(1L)).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void testAddBook() {
        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.addBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void testUpdateBook() {
        Book updatedBook = new Book("Updated Title", "Updated Author", "2022", "0987654321098");
        when(bookService.updateBook(1L, updatedBook)).thenReturn(Optional.of(updatedBook));

        ResponseEntity<Optional<Book>> response = bookController.updateBook(1L, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody().get());
    }

    @Test
    void testDeleteBook() {
        when(bookService.deleteBook(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

}
