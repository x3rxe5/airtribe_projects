## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


Class Diagram
┌─────────────────────┐    ┌──────────────────────┐    ┌─────────────────────┐
│   LibraryManager    │    │  LibraryManagement   │    │     EntityFactory   │
│    (Singleton)      │    │       System         │    │     (Factory)       │
├─────────────────────┤    ├──────────────────────┤    ├─────────────────────┤
│ - repositories      │    │ - bookService        │    │ + createBook()      │
│ - notificationSvc   │    │ - patronService      │    │ + createPatron()    │
│ + getInstance()     │    │ - loanService        │    │ + createLoan()      │
└─────────────────────┘    │ - reservationService │    │ + createReservation│
                           │ + displayStatus()    │    └─────────────────────┘
                           └──────────────────────┘

┌─────────────────────┐    ┌──────────────────────┐    ┌─────────────────────┐
│        Book         │    │       Patron         │    │        Loan         │
├─────────────────────┤    ├──────────────────────┤    ├─────────────────────┤
│ - isbn: String      │    │ - patronId: String   │    │ - loanId: String    │
│ - title: String     │    │ - name: String       │    │ - patronId: String  │
│ - author: String    │    │ - email: String      │    │ - isbn: String      │
│ - publicationYear   │    │ - borrowingHistory   │    │ - borrowDate: Date  │
│ - genre: String     │    │ - preferences: Set   │    │ - dueDate: Date     │
│ - isAvailable: bool │    │ + addPreference()    │    │ - isReturned: bool  │
└─────────────────────┘    └──────────────────────┘    │ + returnBook()      │
                                                       │ + isOverdue(): bool │
                                                       └─────────────────────┘

┌─────────────────────┐    ┌──────────────────────┐    ┌─────────────────────┐
│    BookService      │    │   PatronService      │    │    LoanService      │
├─────────────────────┤    ├──────────────────────┤    ├─────────────────────┤
│ - bookRepository    │    │ - patronRepository   │    │ - bookRepository    │
│ - notificationSvc   │    │ + addPatron()        │    │ - patronRepository  │
│ + addBook()         │    │ + updatePatron()     │    │ - loanRepository    │
│ + searchByTitle()   │    │ + findPatron()       │    │ + checkoutBook()    │
│ + searchByAuthor()  │    │ + addPreference()    │    │ + returnBook()      │
│ + transferBook()    │    └──────────────────────┘    │ + getOverdueLoans() │
└─────────────────────┘                                └─────────────────────┘

┌─────────────────────┐    ┌──────────────────────┐    ┌─────────────────────┐
│ ReservationService  │    │ RecommendationService│    │   BranchService     │
├─────────────────────┤    ├──────────────────────┤    ├─────────────────────┤
│ - reservationQueues │    │ - bookRepository     │    │ - branches: Map     │
│ - bookRepository    │    │ - strategy           │    │ + addBranch()       │
│ + reserveBook()     │    │ + setStrategy()      │    │ + getBranch()       │
│ + processReturn()   │    │ + getRecommendations │    │ + transferBook()    │
└─────────────────────┘    └──────────────────────┘    └─────────────────────┘

┌─────────────────────┐    ┌──────────────────────┐
│ NotificationService │    │ RecommendationStrategy│
│    (Observer)       │    │     (Strategy)       │
├─────────────────────┤    ├──────────────────────┤
│ - observers: List   │    │ + recommend()        │
│ + sendNotification()│    └──────────────────────┘
│ + addObserver()     │              ▲
│ + removeObserver()  │              │
└─────────────────────┘    ┌─────────┴──────────┐
         ▲                 │                    │
         │           GenreBasedStrategy  HistoryBasedStrategy
         │
EmailNotificationService


#### Project Structure
src/
├── domain/
│   ├── Book.java
│   ├── Patron.java
│   ├── Loan.java
│   ├── Reservation.java
│   └── Branch.java
├── exceptions/
│   ├── LibraryException.java
│   ├── BookNotFoundException.java
│   ├── PatronNotFoundException.java
│   └── BookNotAvailableException.java
├── repositories/
│   ├── BookRepository.java (interface)
│   ├── PatronRepository.java (interface)
│   ├── LoanRepository.java (interface)
│   └── impl/
│       ├── InMemoryBookRepository.java
│       ├── InMemoryPatronRepository.java
│       └── InMemoryLoanRepository.java
├── services/
│   ├── BookService.java
│   ├── PatronService.java
│   ├── LoanService.java
│   ├── ReservationService.java
│   ├── RecommendationService.java
│   └── BranchService.java
├── patterns/
│   ├── factory/
│   │   └── EntityFactory.java
│   ├── observer/
│   │   ├── NotificationService.java (interface)
│   │   ├── NotificationObserver.java (interface)
│   │   ├── EmailNotificationService.java
│   │   └── LoggingNotificationObserver.java
│   ├── strategy/
│   │   ├── RecommendationStrategy.java (interface)
│   │   ├── GenreBasedRecommendationStrategy.java
│   │   └── HistoryBasedRecommendationStrategy.java
│   └── singleton/
│       └── LibraryManager.java
├── main/
│   ├── LibraryManagementSystem.java
│   └── LibraryDemo.java
└── README.md