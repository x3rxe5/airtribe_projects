package library_management_system.src.services;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class LoanService {
    private static final Logger logger = Logger.getLogger(LoanService.class.getName());
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;
    private final NotificationService notificationService;
    private final ReservationService reservationService;

    public LoanService(BookRepository bookRepository, PatronRepository patronRepository, 
                      LoanRepository loanRepository, NotificationService notificationService,
                      ReservationService reservationService) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.loanRepository = loanRepository;
        this.notificationService = notificationService;
        this.reservationService = reservationService;
    }

    public String checkoutBook(String patronId, String isbn) throws LibraryException {
        // Validate patron exists
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));

        // Validate book exists and is available
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException(isbn);
        }

        // Create loan
        Loan loan = EntityFactory.createLoan(patronId, isbn);
        loanRepository.save(loan);

        // Update book availability
        book.setAvailable(false);
        bookRepository.save(book);

        // Update patron borrowing history
        patron.addToBorrowingHistory(isbn);
        patronRepository.save(patron);

        logger.info("Book checked out: " + loan);
        notificationService.sendNotification(patronId, "Book checked out: " + book.getTitle());

        return loan.getLoanId();
    }

    public void returnBook(String loanId) throws LibraryException {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LibraryException("Loan not found: " + loanId));

        if (loan.isReturned()) {
            throw new LibraryException("Book already returned");
        }

        // Mark loan as returned
        loan.returnBook();
        loanRepository.save(loan);

        // Make book available again
        Book book = bookRepository.findByIsbn(loan.getIsbn())
                .orElseThrow(() -> new BookNotFoundException(loan.getIsbn()));
        book.setAvailable(true);
        bookRepository.save(book);

        logger.info("Book returned: " + loan);
        notificationService.sendNotification(loan.getPatronId(), "Book returned: " + book.getTitle());

        // Check for reservations
        reservationService.processBookReturn(loan.getIsbn());
    }

    public List<Loan> getPatronLoans(String patronId) {
        return loanRepository.findByPatron(patronId);
    }

    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans();
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public void sendOverdueNotifications() {
        List<Loan> overdueLoans = getOverdueLoans();
        for (Loan loan : overdueLoans) {
            try {
                Book book = bookRepository.findByIsbn(loan.getIsbn()).get();
                String message = String.format("Overdue book: %s (Due: %s)", 
                                              book.getTitle(), loan.getDueDate());
                notificationService.sendNotification(loan.getPatronId(), message);
            } catch (Exception e) {
                logger.warning("Failed to send overdue notification for loan: " + loan.getLoanId());
            }
        }
    }
}