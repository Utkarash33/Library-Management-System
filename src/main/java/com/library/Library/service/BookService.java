package com.library.Library.service;

import com.library.Library.entities.Book;

import java.util.List;

public interface BookService {

    String addBook(Book book);

    String resolveRequestAction(long id, String action);

    String returnBook(long id);
    String reserveBook(long id);
    List<Book> searchBooksByTitle(String query);
}
