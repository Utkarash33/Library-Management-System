package com.library.Library.service;

import com.library.Library.entities.Book;
import com.library.Library.repository.RedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    @Override
    public String addBook(Book book) {

        List<Book> bookList= bookRepository.getAllBooksByTitleAndAuthor(book.getTitle(),book.getAuthor());
        if(bookList.size()!=0)
        {
            throw new IllegalArgumentException("Book already exists");
        }
        return bookRepository.addBook(book);
    }

    @Override
    public String resolveRequestAction(long id, String action)
    {
        String response;
        switch (action) {
            case "return":
                response = returnBook(id);
                break;
            case "reserve":
                response = reserveBook(id);
                break;
            default:
                throw  new IllegalArgumentException("Unable to find the right action.");
        }

        return response;
    }

    @Override
    public String returnBook(long id) {
        Book book = bookRepository.findBookById(id);
        if(book == null)
        {
            throw  new IllegalArgumentException("Book not found!");
        }
        if(!book.isReserved())
        {
            throw new IllegalArgumentException("Book is not reserved");
        }
        return bookRepository.returnBook(book.getId())<=1l ? "Book return successfully, Thank you. :)":"Something went wrong";
    }

    @Override
    public String reserveBook(long id) {
        Book book = bookRepository.findBookById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (book.isReserved()) {
            throw new IllegalStateException("Book is already reserved");
        }
        return bookRepository.reserveBook(book.getId())<=1l ? "Book reserved successfully":"Something went wrong";
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findBooksByTitle(title);


        if(books.size()==0 || books.isEmpty())
        {
            throw new IllegalArgumentException("No book found with "+title);
        }
        return books;
    }
}
