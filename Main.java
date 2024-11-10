package com.example.library;
import com.example.library.dao.BookDAO;
import com.example.library.model.Book;
import com.example.library.util.DBConnection;
import java.sql.Connection;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        BookDAO bookDAO = new BookDAO(connection);
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }
}

