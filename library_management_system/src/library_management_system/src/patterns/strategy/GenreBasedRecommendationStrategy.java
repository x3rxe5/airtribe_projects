package library_management_system.src.patterns.strategy;

import java.util.*;
import java.util.stream.Collectors;

public class GenreBasedRecommendationStrategy implements RecommendationStrategy {
    
    @Override
    public List<Book> recommend(Patron patron, List<Book> availableBooks) {
        Set<String> preferences = patron.getPreferences();
        
        if (preferences.isEmpty()) {
            return availableBooks.stream().limit(5).collect(Collectors.toList());
        }
        
        return availableBooks.stream()
                .filter(book -> preferences.contains(book.getGenre()))
                .limit(5)
                .collect(Collectors.toList());
    }
}