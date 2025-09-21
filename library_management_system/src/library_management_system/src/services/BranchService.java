package library_management_system.src.services;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import library_management_system.src.exceptions.LibraryException;

public class BranchService {
    private static final Logger logger = Logger.getLogger(BranchService.class.getName());
    private final Map<String, Branch> branches = new ConcurrentHashMap<>();
    private final BookRepository bookRepository;

    public BranchService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String addBranch(String name, String address) {
        String branchId = "BR" + UUID.randomUUID().toString().substring(0, 8);
        Branch branch = new Branch(branchId, name, address);
        branches.put(branchId, branch);
        logger.info("Branch added: " + branch);
        return branchId;
    }

    public Branch getBranch(String branchId) throws LibraryException {
        Branch branch = branches.get(branchId);
        if (branch == null) {
            throw new LibraryException("Branch not found: " + branchId);
        }
        return branch;
    }

    public List<Branch> getAllBranches() {
        return new ArrayList<>(branches.values());
    }

    public void addBookToBranch(String branchId, Book book) throws LibraryException {
        Branch branch = getBranch(branchId);
        branch.addBook(book);
        bookRepository.save(book);
        logger.info("Book added to branch " + branchId + ": " + book.getIsbn());
    }

    public List<Book> getBranchInventory(String branchId) throws LibraryException {
        Branch branch = getBranch(branchId);
        return new ArrayList<>(branch.getInventory().values());
    }

    public void transferBookBetweenBranches(String isbn, String fromBranchId, String toBranchId) throws LibraryException {
        Branch fromBranch = getBranch(fromBranchId);
        Branch toBranch = getBranch(toBranchId);
        
        Book book = fromBranch.getBook(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        if (!book.isAvailable()) {
            throw new LibraryException("Cannot transfer book that is currently borrowed");
        }

        fromBranch.removeBook(isbn);
        toBranch.addBook(book);
        bookRepository.save(book);
        
        logger.info("Book transferred from " + fromBranchId + " to " + toBranchId + ": " + isbn);
    }
}