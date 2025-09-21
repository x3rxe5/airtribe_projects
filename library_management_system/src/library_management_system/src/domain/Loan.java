package library_management_system.src.domain;

import java.time.LocalDate;

public class Loan {
    private String loanId;
    private String patronId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public Loan(String loanId, String patronId, String isbn) {
        this.loanId = loanId;
        this.patronId = patronId;
        this.isbn = isbn;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(14); // 2 weeks loan period
        this.isReturned = false;
    }

    // Getters and Setters
    public String getLoanId() { return loanId; }
    public String getPatronId() { return patronId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return isReturned; }
    
    public void returnBook() {
        this.isReturned = true;
        this.returnDate = LocalDate.now();
    }
    
    public boolean isOverdue() {
        return !isReturned && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return String.format("Loan{id='%s', patron='%s', isbn='%s', due=%s, returned=%s}", 
                           loanId, patronId, isbn, dueDate, isReturned);
    }
}