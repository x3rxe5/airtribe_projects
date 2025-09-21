package library_management_system.src.repositories;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);
    Optional<Loan> findById(String loanId);
    List<Loan> findByPatron(String patronId);
    List<Loan> findByBook(String isbn);
    List<Loan> findOverdueLoans();
    List<Loan> findAll();
}
