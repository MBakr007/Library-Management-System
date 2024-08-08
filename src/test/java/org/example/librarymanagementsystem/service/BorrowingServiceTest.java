package org.example.librarymanagementsystem.service;


import org.example.librarymanagementsystem.exception.BorrowingException;
import org.example.librarymanagementsystem.model.BorrowingRecord;
import org.example.librarymanagementsystem.repository.BorrowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.example.librarymanagementsystem.util.Constants.BORROWING_RECORD_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BorrowingServiceTest {

    @Mock
    private BorrowingRepository borrowingRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBookId(1L);
        borrowingRecord.setPatronId(1L);
        borrowingRecord.setBorrowDate(LocalDate.now());
    }

    @Test
    void testBorrowBook_NewBorrowing() {
        when(borrowingRepository.findBorrowingRecordByBookIdAndPatronId(1L, 1L)).thenReturn(null);

        boolean result = borrowingService.borrowBook(1L, 1L);

        assertTrue(result);
        verify(borrowingRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void testBorrowBook_ExistingBorrowing() {
        borrowingRecord.setReturnDate(null);  // Already borrowed and not returned
        when(borrowingRepository.findBorrowingRecordByBookIdAndPatronId(1L, 1L)).thenReturn(borrowingRecord);

        boolean result = borrowingService.borrowBook(1L, 1L);

        assertFalse(result);
        verify(borrowingRepository, times(0)).save(any(BorrowingRecord.class));
    }

    @Test
    void testReturnBook_BorrowingRecordFound() {
        when(borrowingRepository.findBorrowingRecordByBookIdAndPatronId(1L, 1L)).thenReturn(borrowingRecord);

        boolean result = borrowingService.returnBook(1L, 1L);

        assertTrue(result);
        assertNotNull(borrowingRecord.getReturnDate());
        verify(borrowingRepository, times(1)).save(borrowingRecord);
    }

    @Test
    void testReturnBook_BorrowingRecordNotFound() {
        when(borrowingRepository.findBorrowingRecordByBookIdAndPatronId(1L, 1L)).thenReturn(null);

        BorrowingException exception = assertThrows(BorrowingException.class, () -> borrowingService.returnBook(1L, 1L));

        assertEquals(BORROWING_RECORD_NOT_FOUND, exception.getMessage());
        verify(borrowingRepository, times(0)).save(any(BorrowingRecord.class));
    }

    @Test
    void testAddBorrowingRecord() {
        borrowingService.borrowBook(1L, 1L);

        verify(borrowingRepository, times(1)).save(any(BorrowingRecord.class));
    }
}
