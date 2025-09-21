package library_management_system.src.exceptions;

public class BookNotFoundException extends LibraryException {
    public BookNotFoundException(String isbn) {
        super("Book with ISBN " + isbn + " not found");
    }
}