package library_management_system.src.services;

import java.util.*;
import java.util.logging.Logger;

public class RecommendationService {
    private static final Logger logger = Logger.getLogger(RecommendationService.class.getName());
    private final BookRepository bookRepository;
    private RecommendationStrategy strategy;

    public RecommendationService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.strategy = new GenreBasedRecommendationStrategy(); // Default strategy
    }

    public void setRecommendationStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
        logger.info("Recommendation strategy changed to: " + strategy.getClass().getSimpleName());
    }

    public List<Book> getRecommendations(Patron patron) {
        List<Book> availableBooks = bookRepository.findAll().stream()
                .filter(Book::isAvailable)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        List<Book> recommendations = strategy.recommend(patron, availableBooks);
        logger.info("Generated " + recommendations.size() + " recommendations for patron: " + patron.getPatronId());
        return recommendations;
    }
}