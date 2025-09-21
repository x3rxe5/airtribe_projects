package library_management_system.src.repositories.impl;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    @Override
    public void save(Book book) {
        books.put(book.getIsbn(), book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public void delete(String isbn) {
        books.remove(isbn);
    }

    @Override
    public List<Book> findByBranch(String branchId) {
        return books.values().stream()
                .filter(book -> branchId.equals(book.getBranchId()))
                .collect(Collectors.toList());
    }
}

