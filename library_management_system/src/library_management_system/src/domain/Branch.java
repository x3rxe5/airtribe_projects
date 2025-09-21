package library_management_system.src.domain;

import java.util.*;


public class Branch {
    private String branchId;
    private String name;
    private String address;
    private Map<String, Book> inventory;

    public Branch(String branchId, String name, String address) {
        this.branchId = branchId;
        this.name = name;
        this.address = address;
        this.inventory = new HashMap<>();
    }

    public String getBranchId() { return branchId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public Map<String, Book> getInventory() { return new HashMap<>(inventory); }
    
    public void addBook(Book book) {
        book.setBranchId(this.branchId);
        inventory.put(book.getIsbn(), book);
    }
    
    public void removeBook(String isbn) {
        inventory.remove(isbn);
    }
    
    public Book getBook(String isbn) {
        return inventory.get(isbn);
    }

    @Override
    public String toString() {
        return String.format("Branch{id='%s', name='%s', books=%d}", branchId, name, inventory.size());
    }
}