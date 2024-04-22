package com.library.Library.service;

import com.library.Library.entities.Book;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.RedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name ="db_flag", havingValue = "redis")
public class BookSerivceImplRedis implements BookService{
    @Autowired
    private RedisRepo bookRepository;

    @Override
    public String addBook(Book book) {

        if(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(book.getTitle(),book.getAuthor())!=null)
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
        List<Book> listOfBooks = bookRepository.findByTitleContainingIgnoreCase(query);

        System.out.println(listOfBooks);
        if(listOfBooks==null || listOfBooks.size()==0)
        {
            throw new IllegalArgumentException("No book found with "+query);
        }
        return listOfBooks;
    }
}
