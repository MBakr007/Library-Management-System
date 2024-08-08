package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.exception.PatronException;
import org.example.librarymanagementsystem.model.Patron;
import org.example.librarymanagementsystem.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronException("this patron is not found."));
    }

    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Optional<Patron> updatePatron(Long id, Patron updatedPatron) {
        return Optional.ofNullable(patronRepository.findById(id)
                .map(patron -> {
                    patron.setName(updatedPatron.getName());
                    patron.setEmail(updatedPatron.getEmail());
                    patron.setPhoneNumber(updatedPatron.getPhoneNumber());
                    return patronRepository.save(patron);
                })
                .orElseThrow(() -> new PatronException("this patron is not found.")));
    }
    public boolean deletePatron(Long id) {
        return patronRepository.findById(id)
                .map(patron -> {
                    patronRepository.delete(patron);
                    return true;
                })
                .orElseThrow(() -> new PatronException("this patron is not found."));
    }
}
