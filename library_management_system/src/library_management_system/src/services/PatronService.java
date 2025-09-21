package library_management_system.src.services;

import java.util.*;
import java.util.logging.Logger;

public class PatronService {
    private static final Logger logger = Logger.getLogger(PatronService.class.getName());
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public String addPatron(String name, String email, String phone) throws LibraryException {
        try {
            Patron patron = EntityFactory.createPatron(name, email, phone);
            patronRepository.save(patron);
            logger.info("Patron added: " + patron);
            return patron.getPatronId();
        } catch (Exception e) {
            logger.severe("Error adding patron: " + e.getMessage());
            throw new LibraryException("Failed to add patron", e);
        }
    }

    public void updatePatron(String patronId, String name, String email, String phone) throws PatronNotFoundException {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));
        
        patron.setName(name);
        patron.setEmail(email);
        patron.setPhone(phone);
        patronRepository.save(patron);
        logger.info("Patron updated: " + patron);
    }

    public Patron findPatron(String patronId) throws PatronNotFoundException {
        return patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public void addPatronPreference(String patronId, String genre) throws PatronNotFoundException {
        Patron patron = findPatron(patronId);
        patron.addPreference(genre);
        patronRepository.save(patron);
        logger.info("Preference added for patron " + patronId + ": " + genre);
    }

    public List<String> getPatronBorrowingHistory(String patronId) throws PatronNotFoundException {
        Patron patron = findPatron(patronId);
        return patron.getBorrowingHistory();
    }
}