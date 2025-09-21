package library_management_system.src.services;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ReservationService {
    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());
    private final Map<String, Queue<Reservation>> reservationQueues = new ConcurrentHashMap<>();
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final NotificationService notificationService;

    public ReservationService(BookRepository bookRepository, PatronRepository patronRepository,
                            NotificationService notificationService) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.notificationService = notificationService;
    }

    public String reserveBook(String patronId, String isbn) throws LibraryException {
        // Validate patron exists
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));

        // Validate book exists
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        if (book.isAvailable()) {
            throw new LibraryException("Book is currently available for checkout");
        }

        // Create reservation
        Reservation reservation = EntityFactory.createReservation(patronId, isbn);
        
        // Add to queue
        reservationQueues.computeIfAbsent(isbn, k -> new LinkedList<>()).offer(reservation);
        
        logger.info("Book reserved: " + reservation);
        notificationService.sendNotification(patronId, 
            "Book reserved: " + book.getTitle() + ". You are #" + 
            reservationQueues.get(isbn).size() + " in queue.");

        return reservation.getReservationId();
    }

    public void cancelReservation(String reservationId) throws LibraryException {
        for (Map.Entry<String, Queue<Reservation>> entry : reservationQueues.entrySet()) {
            Queue<Reservation> queue = entry.getValue();
            Reservation toRemove = queue.stream()
                    .filter(r -> r.getReservationId().equals(reservationId))
                    .findFirst()
                    .orElse(null);
            
            if (toRemove != null) {
                queue.remove(toRemove);
                toRemove.setActive(false);
                logger.info("Reservation cancelled: " + reservationId);
                return;
            }
        }
        throw new LibraryException("Reservation not found: " + reservationId);
    }

    public void processBookReturn(String isbn) {
        Queue<Reservation> queue = reservationQueues.get(isbn);
        if (queue != null && !queue.isEmpty()) {
            Reservation nextReservation = queue.poll();
            if (nextReservation != null && nextReservation.isActive()) {
                try {
                    Book book = bookRepository.findByIsbn(isbn).get();
                    notificationService.sendNotification(nextReservation.getPatronId(),
                        "Reserved book is now available: " + book.getTitle() + 
                        ". Please check out within 24 hours.");
                    logger.info("Notified next patron in reservation queue: " + nextReservation.getPatronId());
                } catch (Exception e) {
                    logger.warning("Failed to notify patron about available reservation: " + e.getMessage());
                }
            }
        }
    }

    public List<Reservation> getPatronReservations(String patronId) {
        return reservationQueues.values().stream()
                .flatMap(Queue::stream)
                .filter(r -> r.getPatronId().equals(patronId) && r.isActive())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public Map<String, Integer> getReservationCounts() {
        Map<String, Integer> counts = new HashMap<>();
        reservationQueues.forEach((isbn, queue) -> counts.put(isbn, queue.size()));
        return counts;
    }
}
