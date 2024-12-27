package com.lib.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lib.db.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {

    // Assuming DatabaseConnection is a utility class that manages DB connections
    private static DatabaseConnection dbConnection = new DatabaseConnection();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get book details from the request
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        // Create a Book object and save it to the database
        boolean success = addBookToDatabase(title, author);

        // Send a response back to the frontend
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (success) {
            out.write("{\"message\": \"Book added successfully\"}");
        } else {
            out.write("{\"message\": \"Failed to add the book\"}");
        }
    }

    private boolean addBookToDatabase(String title, String author) {
        // Use the DatabaseConnection class to get the connection
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}











