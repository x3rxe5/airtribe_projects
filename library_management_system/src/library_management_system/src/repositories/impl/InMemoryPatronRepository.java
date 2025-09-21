package library_management_system.src.repositories.impl;

import java.util.*;

public class InMemoryPatronRepository implements PatronRepository {
    private final Map<String, Patron> patrons = new HashMap<>();

    @Override
    public void save(Patron patron) {
        patrons.put(patron.getPatronId(), patron);
    }

    @Override
    public Optional<Patron> findById(String patronId) {
        return Optional.ofNullable(patrons.get(patronId));
    }

    @Override
    public List<Patron> findAll() {
        return new ArrayList<>(patrons.values());
    }

    @Override
    public void delete(String patronId) {
        patrons.remove(patronId);
    }
}