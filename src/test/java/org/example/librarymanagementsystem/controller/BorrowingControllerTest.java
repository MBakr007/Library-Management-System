package org.example.librarymanagementsystem.controller;

import org.example.librarymanagementsystem.service.BorrowingService;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BorrowingControllerTest {

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    @Test
    void testBorrowBook_Success() {
        when(borrowingService.borrowBook(1L, 1L)).thenReturn(true);

        ResponseEntity<?> response = borrowingController.borrowBook(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book borrowed successfully.", response.getBody());
    }

    @Test
    void testBorrowBook_AlreadyBorrowed() {
        when(borrowingService.borrowBook(1L, 1L)).thenReturn(false);

        ResponseEntity<?> response = borrowingController.borrowBook(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book is already borrowed.", response.getBody());
    }

    @Test
    void testReturnBook_Success() {
        when(borrowingService.returnBook(1L, 1L)).thenReturn(true);

        ResponseEntity<?> response = borrowingController.returnBook(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book returned successfully.", response.getBody());
    }

    @Test
    void testReturnBook_NotReturned() {
        when(borrowingService.returnBook(1L, 1L)).thenReturn(false);

        ResponseEntity<?> response = borrowingController.returnBook(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book is not returned.", response.getBody());
    }
}
