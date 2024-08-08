package org.example.librarymanagementsystem.repository;

import org.example.librarymanagementsystem.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingRecord, Long> {

    public BorrowingRecord findBorrowingRecordByBookIdAndPatronId(Long bookId, Long patronId);
}
