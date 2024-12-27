package com.lib;

import java.util.List;

import com.lib.model.Book;

public class SearchResponse {
    private List<Book> books;

    public SearchResponse(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
