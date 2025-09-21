package library_management_system.src.repositories.impl;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryLoanRepository implements LoanRepository {
    private final Map<String, Loan> loans = new HashMap<>();

    @Override
    public void save(Loan loan) {
        loans.put(loan.getLoanId(), loan);
    }

    @Override
    public Optional<Loan> findById(String loanId) {
        return Optional.ofNullable(loans.get(loanId));
    }

    @Override
    public List<Loan> findByPatron(String patronId) {
        return loans.values().stream()
                .filter(loan -> loan.getPatronId().equals(patronId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByBook(String isbn) {
        return loans.values().stream()
                .filter(loan -> loan.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findOverdueLoans() {
        return loans.values().stream()
                .filter(Loan::isOverdue)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans.values());
    }
}