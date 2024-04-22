package com.library.Library.repository;

import com.library.Library.entities.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RedisRepo extends CrudRepository<Book, String> {
    List<Book> searchByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);
    Book findById(long id);
    List<Book> findByTitle(String title);
}
