package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.exception.BookException;
import org.example.librarymanagementsystem.model.Book;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.example.librarymanagementsystem.util.Constants.BOOK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("Title", "Author", "2023", "1234567890123");
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
    }

    @Test
    void testGetBookById_BookFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertEquals(book, result);
    }

    @Test
    void testGetBookById_BookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> bookService.getBookById(1L));

        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.saveBook(book);

        assertEquals(book, result);
    }

    @Test
    void testUpdateBook_BookFound() {
        Book updatedBook = new Book("Updated Title", "Updated Author", "2022", "0987654321098");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Optional<Book> result = bookService.updateBook(1L, updatedBook);

        assertTrue(result.isPresent());
        assertEquals(updatedBook.getTitle(), result.get().getTitle());
        assertEquals(updatedBook.getAuthor(), result.get().getAuthor());
        assertEquals(updatedBook.getIsbn(), result.get().getIsbn());
    }

    @Test
    void testUpdateBook_BookNotFound() {
        Book updatedBook = new Book("Updated Title", "Updated Author", "2022", "0987654321098");
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> bookService.updateBook(1L, updatedBook));

        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeleteBook_BookFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.deleteBook(1L);

        assertTrue(result);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_BookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> bookService.deleteBook(1L));

        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }
}
