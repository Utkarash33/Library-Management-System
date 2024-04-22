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
    public void addBook(Book book) {

        String query = "INSERT INTO books (title, author, reserved) VALUES (?,?,?)";
        jdbcTemplate.update(query,book.getTitle(),book.getAuthor(),book.isReserved());
    }

    @Override
    public void returnBook(long bookId) {
        String sql = "UPDATE books SET reserved = FALSE WHERE id = ?";
        jdbcTemplate.update(sql, bookId);
    }

    @Override
    public void reserveBook(long bookId) {
        String sql = "UPDATE books SET reserved = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, bookId);
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
}
