package library_management_system.src.patterns.singleton;

import java.util.logging.Logger;

public class LibraryManager {
    private static final Logger logger = Logger.getLogger(LibraryManager.class.getName());
    private static volatile LibraryManager instance;
    
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;
    private final NotificationService notificationService;
    
    private LibraryManager() {
        this.bookRepository = new InMemoryBookRepository();
        this.patronRepository = new InMemoryPatronRepository();
        this.loanRepository = new InMemoryLoanRepository();
        this.notificationService = new EmailNotificationService();
        
        // Add logging observer
        this.notificationService.addObserver(new LoggingNotificationObserver());
        
        logger.info("LibraryManager initialized");
    }
    
    public static LibraryManager getInstance() {
        if (instance == null) {
            synchronized (LibraryManager.class) {
                if (instance == null) {
                    instance = new LibraryManager();
                }
            }
        }
        return instance;
    }
    
    public BookRepository getBookRepository() { return bookRepository; }
    public PatronRepository getPatronRepository() { return patronRepository; }
    public LoanRepository getLoanRepository() { return loanRepository; }
    public NotificationService getNotificationService() { return notificationService; }
}