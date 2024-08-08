package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.exception.BorrowingException;
import org.example.librarymanagementsystem.model.BorrowingRecord;
import org.example.librarymanagementsystem.repository.BorrowingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.example.librarymanagementsystem.util.Constants.BORROWING_RECORD_NOT_FOUND;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;

    public BorrowingService(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public boolean borrowBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRepository.findBorrowingRecordByBookIdAndPatronId(bookId, patronId);
        if (borrowingRecord == null || borrowingRecord.getReturnDate() != null) {
            addBorrowingRecord(bookId, patronId);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRepository.findBorrowingRecordByBookIdAndPatronId(bookId, patronId);
        if (borrowingRecord != null) {
            borrowingRecord.setReturnDate(LocalDate.now());
            borrowingRepository.save(borrowingRecord);
            return true;
        } else {
            throw new BorrowingException(BORROWING_RECORD_NOT_FOUND);
        }
    }

    private void addBorrowingRecord(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setBookId(bookId);
        borrowingRecord.setPatronId(patronId);
        borrowingRepository.save(borrowingRecord);
    }

}
