package library_management_system.src.patterns.strategy;

import java.util.List;

public interface RecommendationStrategy {
    List<Book> recommend(Patron patron, List<Book> availableBooks);
}