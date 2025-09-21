package library_management_system.src.services;

import java.util.*;
import java.util.logging.Logger;

import library_management_system.src.domain.*;
import library_management_system.src.domain.Book;
import library_management_system.src.exceptions.*;
import library_management_system.src.patterns.factory.*;
import library_management_system.src.patterns.observer.*;
import library_management_system.src.repositories.*;


public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    private final BookRepository bookRepository;
    private final NotificationService notificationService;

    public BookService(BookRepository bookRepository, NotificationService notificationService) {
        this.bookRepository = bookRepository;
        this.notificationService = notificationService;
    }

    public String addBook(String title, String author, int publicationYear, String genre) throws LibraryException {
        try {
            Book book = (Book) EntityFactory.createBook(title, author, publicationYear, genre);
            bookRepository.save(book);
            logger.info("Book added: " + book);
            return book.getIsbn();
        } catch (Exception e) {
            logger.severe("Error adding book: " + e.getMessage());
            throw new LibraryException("Failed to add book", e);
        }
    }

    public void updateBook(String isbn, String title, String author, int publicationYear, String genre) throws BookNotFoundException {
        Optional<Book> existingBook = bookRepository.findByIsbn(isbn);
        if (!existingBook.isPresent()) {
            throw new BookNotFoundException(isbn);
        }

        Book book = new Book(isbn, title, author, publicationYear, genre);
        book.setAvailable(existingBook.get().isAvailable());
        book.setBranchId(existingBook.get().getBranchId());
        bookRepository.save(book);
        logger.info("Book updated: " + book);
    }

    public void removeBook(String isbn) throws BookNotFoundException {
        if (!bookRepository.findByIsbn(isbn).isPresent()) {
            throw new BookNotFoundException(isbn);
        }
        bookRepository.delete(isbn);
        logger.info("Book removed: " + isbn);
    }

    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAll().stream()
                .filter(Book::isAvailable)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public void transferBook(String isbn, String fromBranchId, String toBranchId) throws BookNotFoundException {
        Book book = findBookByIsbn(isbn);
        if (!fromBranchId.equals(book.getBranchId())) {
            throw new LibraryException("Book is not in the specified source branch");
        }
        book.setBranchId(toBranchId);
        bookRepository.save(book);
        logger.info("Book transferred from " + fromBranchId + " to " + toBranchId + ": " + isbn);
    }
}