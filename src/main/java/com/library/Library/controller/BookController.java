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

    @PostMapping("")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        String resposne = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(resposne);
    }

    @PostMapping("/return/{returnId}")
    public ResponseEntity<String> returnBook(@PathVariable long returnId) {
       String resposnse = bookService.returnBook(returnId);
        return ResponseEntity.ok(resposnse);
    }

    @PostMapping("/reserve/{reserveId}")
    public ResponseEntity<String> reserveBook(@PathVariable long reserveId) {
        String resposne = bookService.reserveBook(reserveId);
        return ResponseEntity.ok(resposne);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam("query") String query) {
        List<Book> books = bookService.searchBooksByTitle(query);
        return ResponseEntity.ok(books);
    }
}
