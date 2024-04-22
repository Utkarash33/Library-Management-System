package com.library.Library.controller;


import com.library.Library.entities.Book;
import com.library.Library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        String resposne = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(resposne);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable long id) {
       String resposnse = bookService.returnBook(id);
        return ResponseEntity.ok(resposnse);
    }

    @PostMapping("/reserve/{id}")
    public ResponseEntity<String> reserveBook(@PathVariable long id) {
        String resposne = bookService.reserveBook(id);
        return ResponseEntity.ok(resposne);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("query") String query) {
        List<Book> books = bookService.searchBooks(query);
        return ResponseEntity.ok(books);
    }
}
