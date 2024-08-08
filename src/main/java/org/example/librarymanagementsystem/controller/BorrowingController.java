package org.example.librarymanagementsystem.controller;


import org.example.librarymanagementsystem.service.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping(value = "/borrow/{bookId}/patron/{patronId}", produces = "application/json")
    public ResponseEntity<?> borrowBook(@PathVariable("bookId") Long bookId, @PathVariable("patronId") Long patronId){
        if (borrowingService.borrowBook(bookId, patronId)){
            return new ResponseEntity<>("Book borrowed successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Book is already borrowed.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable("bookId") Long bookId, @PathVariable("patronId") Long patronId){
        if (borrowingService.returnBook(bookId, patronId)) {
            return new ResponseEntity<>("Book returned successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Book is not returned.", HttpStatus.NOT_FOUND);
    }
}
