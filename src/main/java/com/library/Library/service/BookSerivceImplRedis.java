package com.library.Library.service;

import com.library.Library.entities.Book;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.RedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@ConditionalOnProperty(name ="db_flag", havingValue = "redis")
public class BookSerivceImplRedis implements BookService{
    @Autowired
    private RedisRepo bookRepository;

    @Autowired
    private RedisTemplate<String,Book> redisTemplate;



    @Override
    public String addBook(Book book) {

        List<Book> books = new ArrayList<>();

        try(Jedis jedis = new Jedis("redis://localhost:6379")){
            Set<String> bookKeys = jedis.smembers("books");
            for (String key : bookKeys) {
                Map<String, String> bookData = jedis.hgetAll("books:" + key); // Assuming each book key is "books:{id}"

                if (bookData != null && !bookData.isEmpty() && bookData.containsKey("author") && bookData.containsKey("title") && bookData.containsKey("id")) {
                    Book b = new Book();
                    b.setId(Long.parseLong(bookData.get("id")));
                    b.setAuthor(bookData.get("author"));
                    b.setTitle(bookData.get("title"));
                    b.setReserved(Boolean.parseBoolean(bookData.get("reserved")));
                    if (b.getTitle().toLowerCase().contains(book.getTitle().toLowerCase())
                            && b.getAuthor().toLowerCase().contains(book.getAuthor().toLowerCase())) {
                        books.add(b);
                    }
                }
            }

        }
        if(books.size()!=0)
        {
            throw new IllegalArgumentException("Book already exists");
        }
         Book book1 =bookRepository.save(book);
        return book1!=null ?"Book addded successfully":"Not able to add the book";
    }

    @Override
    public String returnBook(long id) {

        Book book = bookRepository.findById(id);

        if(book == null)
        {
            throw  new IllegalArgumentException("Book not found!");
        }
        if(!book.isReserved())
        {
            throw new IllegalArgumentException("Book is not reserved");
        }
        book.setReserved(false);
        Book book1 = bookRepository.findById(id);

        return book!=null ? "Book return successfully, Thank you. :)":"Something went wrong";
    }

    @Override
    public String reserveBook(long id) {
        Book book = bookRepository.findById(id);

        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        if (book.isReserved()) {
            throw new IllegalStateException("Book is already reserved");
        }

        book.setReserved(true);

        return addBook(book);

    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();

        try(Jedis jedis = new Jedis("redis://localhost:6379")){
            Set<String> bookKeys = jedis.smembers("books");
            for (String key : bookKeys) {
                Map<String, String> bookData = jedis.hgetAll("books:" + key);
                System.out.println(bookData);

                if (bookData != null && !bookData.isEmpty()) {
                    Book b = new Book();
                    b.setId(Long.parseLong(bookData.get("id")));
                    b.setAuthor(bookData.get("author"));
                    b.setTitle(bookData.get("title"));
                    b.setReserved(Boolean.parseBoolean(bookData.get("reserved")));
                    System.out.println(b.getTitle().toLowerCase()+" "+query);
                    if (b.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        books.add(b);
                    }
                }
            }

        }
        if(books.size()==0)
        {
            throw new IllegalArgumentException("No book found with "+query);
        }
        return books;
    }
}
