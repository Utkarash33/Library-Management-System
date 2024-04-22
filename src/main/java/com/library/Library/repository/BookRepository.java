package com.library.Library.repository;

import com.library.Library.entities.Book;

import java.util.List;

public interface BookRepository {

  String addBook(Book book);
  String returnBook(long bookId);
  String reserveBook(long bookId);
  List<Book> searchBooks(String query );
  Book findById(long bookId);
  Book findBookByTitleAndAuthor(String title, String author);
}
