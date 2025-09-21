package library_management_system.src.repositories;

import java.util.List;
import java.util.Optional;

public interface PatronRepository {
    void save(Patron patron);
    Optional<Patron> findById(String patronId);
    List<Patron> findAll();
    void delete(String patronId);
}