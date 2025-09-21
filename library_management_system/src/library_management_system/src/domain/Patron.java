package library_management_system.src.domain;

import java.time.LocalDate;
import java.util.*;

public class Patron {
    private String patronId;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;
    private List<String> borrowingHistory;
    private Set<String> preferences;

    public Patron(String patronId, String name, String email, String phone) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = LocalDate.now();
        this.borrowingHistory = new ArrayList<>();
        this.preferences = new HashSet<>();
    }

    // Getters and Setters
    public String getPatronId() { return patronId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public List<String> getBorrowingHistory() { return new ArrayList<>(borrowingHistory); }
    public void addToBorrowingHistory(String isbn) { borrowingHistory.add(isbn); }
    public Set<String> getPreferences() { return new HashSet<>(preferences); }
    public void addPreference(String genre) { preferences.add(genre); }

    @Override
    public String toString() {
        return String.format("Patron{id='%s', name='%s', email='%s'}", patronId, name, email);
    }
}
