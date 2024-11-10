// Import required libraries and packages
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Model Classes

class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private boolean available;

    // Constructors
    public Book() {}
    public Book(String title, String author, String genre, String isbn, boolean available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.available = available;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', genre='" + genre + "', isbn='" + isbn + "', available=" + available + '}';
    }
}

class User {
    private int id;
    private String name;
    private String email;
    private String phone;

    // Constructors
    public User() {}
    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "', phone='" + phone + "'}";
    }
}

class Transaction {
    private int id;
    private int userId;
    private int bookId;
    private Date borrowDate;
    private Date returnDate;
    private double fineAmount;

    // Constructors
    public Transaction() {}
    public Transaction(int userId, int bookId, Date borrowDate, Date returnDate, double fineAmount) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", borrowDate=" + borrowDate + ", returnDate=" + returnDate + ", fineAmount=" + fineAmount + '}';
    }
}

// Database Connection Utility

class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

// DAO Class for Book Operations

class BookDAO {
    private Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setIsbn(rs.getString("isbn"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Additional methods for addBook, updateBook, deleteBook can be implemented here
}

// Main Class to Test DAO Operations

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to the database!");
            return;
        }

        BookDAO bookDAO = new BookDAO(connection);

        // Fetch all books and display
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Books in the library:");
            for (Book book : books) {
                System.out.println(book);
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

