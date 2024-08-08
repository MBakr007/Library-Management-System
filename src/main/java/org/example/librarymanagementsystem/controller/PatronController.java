package org.example.librarymanagementsystem.controller;


import org.example.librarymanagementsystem.model.Patron;
import org.example.librarymanagementsystem.service.PatronService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        return new ResponseEntity<>(patronService.getAllPatrons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(patronService.getPatronById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        return new ResponseEntity<>(patronService.savePatron(patron), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Patron>> updatePatron(@PathVariable("id") Long id, @RequestBody Patron patron) {
        return new ResponseEntity<>(patronService.updatePatron(id, patron), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatron(@PathVariable("id") Long id) {
        return new ResponseEntity<>(patronService.deletePatron(id), HttpStatus.OK);
    }
}
