package library_management_system.src.exceptions;

public class BookNotAvailableException extends LibraryException {
    public BookNotAvailableException(String isbn) {
        super("Book with ISBN " + isbn + " is not available for checkout");
    }
}