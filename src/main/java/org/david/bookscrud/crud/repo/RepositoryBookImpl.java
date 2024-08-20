package org.david.bookscrud.crud.repo;

import org.david.bookscrud.models.dtos.Author;
import org.david.bookscrud.models.dtos.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryBookImpl implements IRepositoryBook{

    private Connection conn;

    public RepositoryBookImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public List<Book> executeGetAll() {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement("select b.book_id, b.title, b.status, b.reflex, a.* from books as b " +
                "left join authors as a on b.author = a.author_id")){
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                books.add(createBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return books;
    }

    @Override
    public List<Book> executeByAuthor(String nameAuthor) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement("select a.*, b.book_id, b.title, b.status, b.reflex from authors as a " +
                "left join books as b on b.author = a.author_id where a.name = ?")){
            pre.setString(1, nameAuthor);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                books.add(createBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return books;
    }

    @Override
    public List<Book> executeByStatus(String status) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement("select b.book_id, b.title, b.status, b.reflex, a.* from books as b " +
                "left join authors as a on b.author = a.author_id where b.status = ?")){
            pre.setString(1, status);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                books.add(createBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return books;
    }

    @Override
    public Optional<Book> executeByTitle(String title) {
        Book book = null;
        try (PreparedStatement pre = conn.prepareStatement("select b.book_id, b.title, b.status, b.reflex, a.* from books as b " +
                "left join authors as a on b.author = a.author_id where b.title = ?")){
//            select b.book_id, b.title, b.status, b.reflex, a.* from books as b left join authors as a on b.author = a.author_id where b.title = ?
            pre.setString(1, title);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                book = createBook(rs);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void executePut(Book book) {
        String query;
        if (book.getBookId() != null && book.getBookId() > 0){
            query = "update books set title=?, author=?, status=?, reflex=? where book_id=?";
        } else {
            query = "insert into books (title, author, status, reflex) values (?,?,?,?)";
        }

        try (PreparedStatement pre = conn.prepareStatement(query)){
            pre.setString(1, book.getTitle());
            pre.setInt(2, book.getAuthor().getAuthorId());
            pre.setString(3, book.getStatus());
            pre.setString(4, book.getReflex());
            if (book.getBookId() != null && book.getBookId() > 0){
                pre.setInt(5, book.getBookId());
            }
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    private Book createBook(ResultSet rs) throws SQLException {
        Author author = new Author(rs.getInt("author_id"), rs.getString("name"));
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(author);
        book.setStatus(rs.getString("status"));
        book.setReflex(rs.getString("reflex"));
        return book;
    }
}
