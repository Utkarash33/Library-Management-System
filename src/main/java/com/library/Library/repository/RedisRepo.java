package com.library.Library.repository;

import com.library.Library.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Repository
public class RedisRepo
{
    @Autowired
    private Jedis jedis;

    public List<Book> getAllBooksByTitleAndAuthor(String title, String author)
    {
        List<Book> books = new ArrayList<>();

        Set<String> bookKeys = jedis.smembers("books");
        for (String key : bookKeys) {
            Map<String, String> bookData = jedis.hgetAll("books:" + key); // Assuming each book key is "books:{id}"

            if (bookData != null && !bookData.isEmpty() && bookData.containsKey("author") && bookData.containsKey("title") && bookData.containsKey("id")) {
                Book b = new Book();
                b.setId(Long.parseLong(bookData.get("id")));
                b.setAuthor(bookData.get("author"));
                b.setTitle(bookData.get("title"));
                b.setReserved(Boolean.parseBoolean(bookData.get("reserved")));
                if (b.getTitle().toLowerCase().contains(title.toLowerCase())
                        && b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                    books.add(b);
                }
            }
        }
        return books;
    }

    public String addBook(Book book) {
        jedis.hset("books:" + book.getId(), "id", String.valueOf(book.getId()));
        jedis.hset("books:" + book.getId(), "author", book.getAuthor());
        jedis.hset("books:" + book.getId(), "title", book.getTitle());
        jedis.hset("books:" + book.getId(), "reserved", String.valueOf(book.isReserved()));
        jedis.sadd("books", String.valueOf(book.getId()));
        return "Book added successfully";
    }

    public long returnBook(long bookId) {
        return jedis.hset("books:" + bookId, "reserved", String.valueOf(false));
    }

    public long reserveBook(long bookId) {
       return jedis.hset("books:" + bookId, "reserved", String.valueOf(true));
    }

    public Book findBookById(long id) {
        Map<String, String> bookData = jedis.hgetAll("books:" + id);
        if (bookData != null && !bookData.isEmpty()) {
            Book b = new Book();
            b.setId(Long.parseLong(bookData.get("id")));
            b.setAuthor(bookData.get("author"));
            b.setTitle(bookData.get("title"));
            b.setReserved(Boolean.parseBoolean(bookData.get("reserved")));
            return b;
        }
        return null;
    }

    public List<Book> findBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();

        Set<String> bookKeys = jedis.smembers("books");
        for (String key : bookKeys) {
            Map<String, String> bookData = jedis.hgetAll("books:" + key);
            if (bookData != null && !bookData.isEmpty() && bookData.containsKey("title")) {
                String bookTitle = bookData.get("title");
                if (bookTitle != null && bookTitle.toLowerCase().contains(title.toLowerCase())) {
                    Book b = new Book();
                    b.setId(Long.parseLong(bookData.get("id")));
                    b.setAuthor(bookData.get("author"));
                    b.setTitle(bookTitle);
                    b.setReserved(Boolean.parseBoolean(bookData.get("reserved")));
                    books.add(b);
                }
            }
        }
        return books;
    }
     public long incremetReturnCount()
     {
        return jedis.hincrBy("count_return_reserves","return_count",1);
     }

    public long incremetReserveCount()
    {
        return jedis.hincrBy("count_return_reserves","reserve_count",1);
    }
}
