package library_management_system.src.main;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibraryDemo {
    private static final Logger logger = Logger.getLogger(LibraryDemo.class.getName());
    private static LibraryManagementSystem library;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Set logging level
        Logger.getLogger("").setLevel(Level.INFO);
        
        library = new LibraryManagementSystem();
        
        System.out.println("Welcome to the Library Management System!");
        setupSampleData();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: addBook(); break;
                case 2: searchBooks(); break;
                case 3: addPatron(); break;
                case 4: checkoutBook(); break;
                case 5: returnBook(); break;
                case 6: reserveBook(); break;
                case 7: getRecommendations(); break;
                case 8: manageBranches(); break;
                case 9: viewSystemStatus(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
        
        System.out.println("Thank you for using the Library Management System!");
    }
    
    private static void setupSampleData() {
        System.out.println("\nSetting up sample data...");
        
        try {
            // Add sample books
            String isbn1 = library.getBookService().addBook("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Classic");
            String isbn2 = library.getBookService().addBook("To Kill a Mockingbird", "Harper Lee", 1960, "Classic");
            String isbn3 = library.getBookService().addBook("1984", "George Orwell", 1949, "Dystopian");
            String isbn4 = library.getBookService().addBook("The Catcher in the Rye", "J.D. Salinger", 1951, "Classic");
            String isbn5 = library.getBookService().addBook("Dune", "Frank Herbert", 1965, "Science Fiction");
            
            // Add sample patrons
            String patron1 = library.getPatronService().addPatron("Alice Johnson", "alice@email.com", "555-0101");
            String patron2 = library.getPatronService().addPatron("Bob Smith", "bob@email.com", "555-0102");
            String patron3 = library.getPatronService().addPatron("Carol Davis", "carol@email.com", "555-0103");
            
            // Add preferences
            library.getPatronService().addPatronPreference(patron1, "Classic");
            library.getPatronService().addPatronPreference(patron2, "Science Fiction");
            library.getPatronService().addPatronPreference(patron3, "Dystopian");
            
            // Add branches
            String mainBranch = library.getBranchService().addBranch("Main Library", "123 Main St");
            String eastBranch = library.getBranchService().addBranch("East Branch", "456 East Ave");
            
            System.out.println("Sample data setup complete!");
            
        } catch (Exception e) {
            System.err.println("Error setting up sample data: " + e.getMessage());
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
        System.out.println("1. Add Book");
        System.out.println("2. Search Books");
        System.out.println("3. Add Patron");
        System.out.println("4. Checkout Book");
        System.out.println("5. Return Book");
        System.out.println("6. Reserve Book");
        System.out.println("7. Get Recommendations");
        System.out.println("8. Manage Branches");
        System.out.println("9. View System Status");
        System.out.println("0. Exit");
        System.out.println("================================");
    }
    
    private static void addBook() {
        System.out.println("\n--- Add Book ---");
        String title = getStringInput("Enter title: ");
        String author = getStringInput("Enter author: ");
        int year = getIntInput("Enter publication year: ");
        String genre = getStringInput("Enter genre: ");
        
        try {
            String isbn = library.getBookService().addBook(title, author, year, genre);
            System.out.println("Book added successfully! ISBN: " + isbn);
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }
    
    private static void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("1. Search by title");
        System.out.println("2. Search by author");
        System.out.println("3. Show all books");
        
        int choice = getIntInput("Enter search type: ");
        List<Book> results = null;
        
        try {
            switch (choice) {
                case 1:
                    String title = getStringInput("Enter title to search: ");
                    results = library.getBookService().searchByTitle(title);
                    break;
                case 2:
                    String author = getStringInput("Enter author to search: ");
                    results = library.getBookService().searchByAuthor(author);
                    break;
                case 3:
                    results = library.getBookService().getAllBooks();
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }
            
            if (results.isEmpty()) {
                System.out.println("No books found");
            } else {
                System.out.println("\nFound " + results.size() + " book(s):");
                for (Book book : results) {
                    System.out.println("- " + book);
                }
            }
        } catch (Exception e) {
            System.err.println("Error searching books: " + e.getMessage());
        }
    }
    
    private static void addPatron() {
        System.out.println("\n--- Add Patron ---");
        String name = getStringInput("Enter name: ");
        String email = getStringInput("Enter email: ");
        String phone = getStringInput("Enter phone: ");
        
        try {
            String patronId = library.getPatronService().addPatron(name, email, phone);
            System.out.println("Patron added successfully! ID: " + patronId);
        } catch (Exception e) {
            System.err.println("Error adding patron: " + e.getMessage());
        }
    }
    
    private static void checkoutBook() {
        System.out.println("\n--- Checkout Book ---");
        String patronId = getStringInput("Enter patron ID: ");
        String isbn = getStringInput("Enter book ISBN: ");
        
        try {
            String loanId = library.getLoanService().checkoutBook(patronId, isbn);
            System.out.println("Book checked out successfully! Loan ID: " + loanId);
        } catch (Exception e) {
            System.err.println("Error checking out book: " + e.getMessage());
        }
    }
    
    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        String loanId = getStringInput("Enter loan ID: ");
        
        try {
            library.getLoanService().returnBook(loanId);
            System.out.println("Book returned successfully!");
        } catch (Exception e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
    }
    
    private static void reserveBook() {
        System.out.println("\n--- Reserve Book ---");
        String patronId = getStringInput("Enter patron ID: ");
        String isbn = getStringInput("Enter book ISBN: ");
        
        try {
            String reservationId = library.getReservationService().reserveBook(patronId, isbn);
            System.out.println("Book reserved successfully! Reservation ID: " + reservationId);
        } catch (Exception e) {
            System.err.println("Error reserving book: " + e.getMessage());
        }
    }
    
    private static void getRecommendations() {
        System.out.println("\n--- Get Recommendations ---");
        String patronId = getStringInput("Enter patron ID: ");
        
        try {
            Patron patron = library.getPatronService().findPatron(patronId);
            
            // Try different recommendation strategies
            System.out.println("Genre-based recommendations:");
            library.getRecommendationService().setRecommendationStrategy(new GenreBasedRecommendationStrategy());
            List<Book> genreRecs = library.getRecommendationService().getRecommendations(patron);
            displayRecommendations(genreRecs);
            
            System.out.println("\nHistory-based recommendations:");
            library.getRecommendationService().setRecommendationStrategy(
                new HistoryBasedRecommendationStrategy(LibraryManager.getInstance().getBookRepository()));
            List<Book> historyRecs = library.getRecommendationService().getRecommendations(patron);
            displayRecommendations(historyRecs);
            
        } catch (Exception e) {
            System.err.println("Error getting recommendations: " + e.getMessage());
        }
    }
    
    private static void displayRecommendations(List<Book> recommendations) {
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations available");
        } else {
            for (Book book : recommendations) {
                System.out.println("- " + book);
            }
        }
    }
    
    private static void manageBranches() {
        System.out.println("\n--- Manage Branches ---");
        System.out.println("1. Add Branch");
        System.out.println("2. View All Branches");
        System.out.println("3. View Branch Inventory");
        
        int choice = getIntInput("Enter choice: ");
        
        try {
            switch (choice) {
                case 1:
                    String name = getStringInput("Enter branch name: ");
                    String address = getStringInput("Enter address: ");
                    String branchId = library.getBranchService().addBranch(name, address);
                    System.out.println("Branch added! ID: " + branchId);
                    break;
                case 2:
                    List<Branch> branches = library.getBranchService().getAllBranches();
                    System.out.println("All branches:");
                    for (Branch branch : branches) {
                        System.out.println("- " + branch);
                    }
                    break;
                case 3:
                    String branchIdToView = getStringInput("Enter branch ID: ");
                    List<Book> inventory = library.getBranchService().getBranchInventory(branchIdToView);
                    System.out.println("Branch inventory:");
                    for (Book book : inventory) {
                        System.out.println("- " + book);
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            System.err.println("Error managing branches: " + e.getMessage());
        }
    }
    
    private static void viewSystemStatus() {
        library.displaySystemStatus();
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}
