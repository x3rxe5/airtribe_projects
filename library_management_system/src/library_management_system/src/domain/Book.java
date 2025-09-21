package library_management_system.src.domain;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private String genre;
    private boolean isAvailable;
    private String branchId;

    public Book(String isbn, String title, String author, int publicationYear, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    @Override
    public String toString() {
        return String.format("Book{isbn='%s', title='%s', author='%s', year=%d, available=%s}", 
                           isbn, title, author, publicationYear, isAvailable);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}