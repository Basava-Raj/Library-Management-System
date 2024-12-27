package com.lib.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.SearchResponse;
import com.lib.dao.LibraryDAO;
import com.lib.daoImpl.LibraryDAOImpl;
import com.lib.db.DatabaseConnection;
import com.lib.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchBookServlet extends HttpServlet {
    private LibraryDAO libraryDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize DAO
        libraryDAO = new LibraryDAOImpl(DatabaseConnection.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");

        List<Book> books = libraryDAO.searchBooks(query);

        // Sending response
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        out.write(objectMapper.writeValueAsString(new SearchResponse(books)));
        out.flush();
    }
}
