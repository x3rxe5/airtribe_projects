package library_management_system.src.patterns.strategy;

import java.util.*;
import java.util.stream.Collectors;

public class HistoryBasedRecommendationStrategy implements RecommendationStrategy {
    private final BookRepository bookRepository;

    public HistoryBasedRecommendationStrategy(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> recommend(Patron patron, List<Book> availableBooks) {
        List<String> history = patron.getBorrowingHistory();
        
        if (history.isEmpty()) {
            return availableBooks.stream().limit(5).collect(Collectors.toList());
        }
        
        // Get genres from borrowing history
        Set<String> preferredGenres = history.stream()
                .map(isbn -> bookRepository.findByIsbn(isbn))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Book::getGenre)
                .collect(Collectors.toSet());
        
        return availableBooks.stream()
                .filter(book -> preferredGenres.contains(book.getGenre()))
                .filter(book -> !history.contains(book.getIsbn()))
                .limit(5)
                .collect(Collectors.toList());
    }
}