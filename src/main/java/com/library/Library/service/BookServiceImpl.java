package com.library.Library.service;

import com.library.Library.entities.Book;
import com.library.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name ="db_flag", havingValue = "mysql")
public class BookServiceImpl implements  BookService{


    @Autowired
    private BookRepository bookRepository;

    @Override
    public String addBook(Book book) {

        if(bookRepository.findBookByTitleAndAuthor(book.getTitle(),book.getAuthor())!=null)
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
       return bookRepository.returnBook(id);
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
        return bookRepository.reserveBook(id);

    }

    @Override
    public List<Book> searchBooksByTitle(String query) {
        List<Book> listOfBooks = bookRepository.searchBooks(query);

        System.out.println(listOfBooks);
        if(listOfBooks==null || listOfBooks.size()==0)
        {
            throw new IllegalArgumentException("No book found with "+query);
        }
        return listOfBooks;
    }
}
