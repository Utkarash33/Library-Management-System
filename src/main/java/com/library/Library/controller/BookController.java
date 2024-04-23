package com.library.Library.controller;


import com.library.Library.configurations.CustomKafkaProducer;
import com.library.Library.entities.Book;
import com.library.Library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomKafkaProducer kafkaProducer;

    @PostMapping("")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        String resposne = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(resposne);
    }

    @PutMapping("")
    public ResponseEntity<String> processBookAction(@RequestBody Map<String, Object> requestBody) {
        String action = (String) requestBody.get("action");
        Long id = ((Number) requestBody.get("id")).longValue();

        String response =bookService.resolveRequestAction(id,action);

        kafkaProducer.sendEvent(action);

        return ResponseEntity.ok(response);
    }


    @GetMapping("")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam("title") String query) {
        List<Book> books = bookService.searchBooksByTitle(query);

        return ResponseEntity.ok(books);
    }
}
