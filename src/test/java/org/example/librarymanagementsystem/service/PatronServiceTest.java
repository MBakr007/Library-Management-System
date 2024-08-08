package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.exception.PatronException;
import org.example.librarymanagementsystem.model.Patron;
import org.example.librarymanagementsystem.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.example.librarymanagementsystem.util.Constants.PATRON_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron("John Doe", "johndoe@example.com", "123-456-7890");
    }

    @Test
    void testGetAllPatrons() {
        List<Patron> patrons = Arrays.asList(patron);
        when(patronRepository.findAll()).thenReturn(patrons);

        List<Patron> result = patronService.getAllPatrons();

        assertEquals(1, result.size());
        assertEquals(patron, result.get(0));
    }

    @Test
    void testGetPatronById_PatronFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        Patron result = patronService.getPatronById(1L);

        assertEquals(patron, result);
    }

    @Test
    void testGetPatronById_PatronNotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        PatronException exception = assertThrows(PatronException.class, () -> patronService.getPatronById(1L));

        assertEquals(PATRON_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testSavePatron() {
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        Patron result = patronService.savePatron(patron);

        assertEquals(patron, result);
    }

    @Test
    void testUpdatePatron_PatronFound() {
        Patron updatedPatron = new Patron("Jane Doe", "janedoe@example.com", "098-765-4321");
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(updatedPatron);

        Optional<Patron> result = patronService.updatePatron(1L, updatedPatron);

        assertTrue(result.isPresent());
        assertEquals(updatedPatron.getName(), result.get().getName());
        assertEquals(updatedPatron.getEmail(), result.get().getEmail());
        assertEquals(updatedPatron.getPhoneNumber(), result.get().getPhoneNumber());
    }

    @Test
    void testUpdatePatron_PatronNotFound() {
        Patron updatedPatron = new Patron("Jane Doe", "janedoe@example.com", "098-765-4321");
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        PatronException exception = assertThrows(PatronException.class, () -> patronService.updatePatron(1L, updatedPatron));

        assertEquals(PATRON_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeletePatron_PatronFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        boolean result = patronService.deletePatron(1L);

        assertTrue(result);
        verify(patronRepository, times(1)).delete(patron);
    }

    @Test
    void testDeletePatron_PatronNotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        PatronException exception = assertThrows(PatronException.class, () -> patronService.deletePatron(1L));

        assertEquals(PATRON_NOT_FOUND, exception.getMessage());
    }
}
