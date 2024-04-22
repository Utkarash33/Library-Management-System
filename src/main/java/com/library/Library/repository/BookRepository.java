package com.library.Library.repository;

import com.library.Library.entities.Book;

import java.util.List;

public interface BookRepository {

  void addBook(Book book);
  void returnBook(long bookId);
  void reserveBook(long bookId);
  List<Book> searchBooks(String query );
  Book findById(long bookId);

}
