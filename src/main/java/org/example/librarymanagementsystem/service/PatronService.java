package org.example.librarymanagementsystem.service;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.example.librarymanagementsystem.exception.PatronException;
import org.example.librarymanagementsystem.model.Patron;
import org.example.librarymanagementsystem.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.example.librarymanagementsystem.util.Constants.PATRON_NOT_FOUND;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @PostConstruct
    public void saveFakeBooks(){
        Faker faker = new Faker();

        List<Patron> patrons = IntStream.range(0, 10) // generate 10 books
                .mapToObj(i -> new Patron(
                        String.valueOf(faker.name()),
                        String.valueOf(faker.phoneNumber()),
                        faker.internet().emailAddress())
                )
                .toList();

        patrons.forEach(this::savePatron);
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronException(PATRON_NOT_FOUND));
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
                .orElseThrow(() -> new PatronException(PATRON_NOT_FOUND)));
    }

    public boolean deletePatron(Long id) {
        return patronRepository.findById(id)
                .map(patron -> {
                    patronRepository.delete(patron);
                    return true;
                })
                .orElseThrow(() -> new PatronException(PATRON_NOT_FOUND));
    }
}
