package org.example.librarymanagementsystem.controller;


import org.example.librarymanagementsystem.model.Patron;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.librarymanagementsystem.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron("John Doe", "johndoe@example.com", "1234567890");
    }

    @Test
    void testGetAllPatrons() {
        List<Patron> patrons = Arrays.asList(patron);
        when(patronService.getAllPatrons()).thenReturn(patrons);

        ResponseEntity<List<Patron>> response = patronController.getAllPatrons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patrons, response.getBody());
    }

    @Test
    void testGetPatronById() {
        when(patronService.getPatronById(1L)).thenReturn(patron);

        ResponseEntity<Patron> response = patronController.getPatronById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patron, response.getBody());
    }

    @Test
    void testAddPatron() {
        when(patronService.savePatron(any(Patron.class))).thenReturn(patron);

        ResponseEntity<Patron> response = patronController.addPatron(patron);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(patron, response.getBody());
    }

    @Test
    void testUpdatePatron() {
        Patron updatedPatron = new Patron("Jane Doe", "janedoe@example.com", "0987654321");
        when(patronService.updatePatron(1L, updatedPatron)).thenReturn(Optional.of(updatedPatron));

        ResponseEntity<Optional<Patron>> response = patronController.updatePatron(1L, updatedPatron);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPatron, response.getBody().get());
    }

    @Test
    void testDeletePatron() {
        when(patronService.deletePatron(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = patronController.deletePatron(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }
}
