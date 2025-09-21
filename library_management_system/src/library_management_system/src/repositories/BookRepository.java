package library_management_system.src.repositories;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findAll();
    void delete(String isbn);
    List<Book> findByBranch(String branchId);
}
