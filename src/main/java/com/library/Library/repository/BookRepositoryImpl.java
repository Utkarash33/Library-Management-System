package com.library.Library.repository;

import com.library.Library.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements  BookRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public String addBook(Book book) {

        String query = "INSERT INTO books (title, author, reserved) VALUES (?,?,?)";
       int rows = jdbcTemplate.update(query,book.getTitle(),book.getAuthor(),book.isReserved());
        if (rows > 0) {
           return "Book added successfully";
        } else {
            return "Not able to add the book";
        }
    }

    @Override
    public String returnBook(long bookId) {
        String sql = "UPDATE books SET reserved = FALSE WHERE id = ?";
        int rows = jdbcTemplate.update(sql, bookId);
        if (rows > 0) {
            return "Book return successfully, Thank you. :)";
        } else {
            return "Something went wrong.";
        }
    }

    @Override
    public String reserveBook(long bookId) {
        String sql = "UPDATE books SET reserved = TRUE WHERE id = ?";
        int rows = jdbcTemplate.update(sql, bookId);
        if (rows > 0) {
            return "Book reserved successfully";
        } else {
           return "Not able to reserved the book";
        }
    }

    @Override
    public List<Book> searchBooks(String query) {
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + query + "%"}, (rs, rowNum) ->
                new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("reserved")
                ));
    }

    @Override
    public  Book findById(long bookId)
    {
        String sql = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{bookId}, (rs, rowNum) ->
                new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("reserved")
                ));
    }

    @Override
    public Book findBookByTitleAndAuthor(String title, String author) {
        String sql = "SELECT * FROM books WHERE title = ? AND author = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{title, author}, (rs, rowNum) ->
                    new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getBoolean("reserved")
                    ));
        } catch (Exception e) {
            return null;
        }
    }
}
