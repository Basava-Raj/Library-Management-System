package com.lib.dao;

import java.util.List;

import com.lib.model.Book;

public interface LibraryDAO {
    void addBook(Book book);
    void borrowBook(int bookId, int userId);
    void returnBook(int bookId, int userId);
    List<Book> searchBooks(String query);
}

