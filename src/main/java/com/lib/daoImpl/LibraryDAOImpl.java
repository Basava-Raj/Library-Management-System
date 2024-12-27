package com.lib.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lib.dao.LibraryDAO;
import com.lib.model.Book;

public class LibraryDAOImpl implements LibraryDAO {
    private Connection connection;

    public LibraryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addBook(Book book) {
        try {
            String query = "INSERT INTO Books (title, author, is_borrowed) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setBoolean(3, book.isBorrowed());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void borrowBook(int bookId, int userId) {
        try {
            String query = "UPDATE Books SET is_borrowed = true WHERE book_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.executeUpdate();

            String borrowQuery = "INSERT INTO BorrowedBooks (user_id, book_id, borrow_date) VALUES (?, ?, ?)";
            PreparedStatement borrowStmt = connection.prepareStatement(borrowQuery);
            borrowStmt.setInt(1, userId);
            borrowStmt.setInt(2, bookId);
            borrowStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            borrowStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(int bookId, int userId) {
        try {
            String query = "UPDATE Books SET is_borrowed = false WHERE book_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.executeUpdate();

            String returnQuery = "UPDATE BorrowedBooks SET return_date = ? WHERE user_id = ? AND book_id = ?";
            PreparedStatement returnStmt = connection.prepareStatement(returnQuery);
            returnStmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            returnStmt.setInt(2, userId);
            returnStmt.setInt(3, bookId);
            returnStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        try {
            String searchQuery = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setBorrowed(rs.getBoolean("is_borrowed"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
