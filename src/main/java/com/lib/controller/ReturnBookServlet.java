package com.lib.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.ResponseMessage;
import com.lib.dao.LibraryDAO;
import com.lib.daoImpl.LibraryDAOImpl;
import com.lib.db.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReturnBookServlet extends HttpServlet {
    private LibraryDAO libraryDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize DAO
        libraryDAO = new LibraryDAOImpl(DatabaseConnection.getConnection());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        libraryDAO.returnBook(bookId, userId);

        // Sending response
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        out.write(objectMapper.writeValueAsString(new ResponseMessage("Book returned successfully")));
        out.flush();
    }
}
