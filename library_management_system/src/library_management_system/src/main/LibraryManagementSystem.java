package library_management_system.src.main;

import java.util.*;
import java.util.logging.Logger;

public class LibraryManagementSystem {
    private static final Logger logger = Logger.getLogger(LibraryManagementSystem.class.getName());
    
    private final BookService bookService;
    private final PatronService patronService;
    private final LoanService loanService;
    private final ReservationService reservationService;
    private final RecommendationService recommendationService;
    private final BranchService branchService;
    
    public LibraryManagementSystem() {
        LibraryManager manager = LibraryManager.getInstance();
        
        this.bookService = new BookService(
            manager.getBookRepository(), 
            manager.getNotificationService()
        );
        
        this.patronService = new PatronService(manager.getPatronRepository());
        
        this.reservationService = new ReservationService(
            manager.getBookRepository(),
            manager.getPatronRepository(),
            manager.getNotificationService()
        );
        
        this.loanService = new LoanService(
            manager.getBookRepository(),
            manager.getPatronRepository(),
            manager.getLoanRepository(),
            manager.getNotificationService(),
            this.reservationService
        );
        
        this.recommendationService = new RecommendationService(manager.getBookRepository());
        this.branchService = new BranchService(manager.getBookRepository());
        
        logger.info("Library Management System initialized successfully");
    }
    
    // Getters for services
    public BookService getBookService() { return bookService; }
    public PatronService getPatronService() { return patronService; }
    public LoanService getLoanService() { return loanService; }
    public ReservationService getReservationService() { return reservationService; }
    public RecommendationService getRecommendationService() { return recommendationService; }
    public BranchService getBranchService() { return branchService; }
    
    // Utility methods
    public void displaySystemStatus() {
        System.out.println("\n=== LIBRARY SYSTEM STATUS ===");
        System.out.println("Total Books: " + bookService.getAllBooks().size());
        System.out.println("Available Books: " + bookService.getAvailableBooks().size());
        System.out.println("Total Patrons: " + patronService.getAllPatrons().size());
        System.out.println("Active Loans: " + loanService.getAllLoans().stream()
            .filter(loan -> !loan.isReturned()).count());
        System.out.println("Overdue Loans: " + loanService.getOverdueLoans().size());
        System.out.println("Total Branches: " + branchService.getAllBranches().size());
    }
    
    public void sendOverdueNotifications() {
        loanService.sendOverdueNotifications();
        System.out.println("Overdue notifications sent");
    }
}
