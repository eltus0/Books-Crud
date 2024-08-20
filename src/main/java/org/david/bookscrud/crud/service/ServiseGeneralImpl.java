package org.david.bookscrud.crud.service;

import org.david.bookscrud.crud.repo.IRepositoryAuthor;
import org.david.bookscrud.crud.repo.IRepositoryBook;
import org.david.bookscrud.crud.repo.RepositoryAuthorImpl;
import org.david.bookscrud.crud.repo.RepositoryBookImpl;
import org.david.bookscrud.models.dtos.Author;
import org.david.bookscrud.models.dtos.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiseGeneralImpl implements IService {
    private IRepositoryBook bookRepo;
    private IRepositoryAuthor authorRepo;

    private Connection conn;

    public ServiseGeneralImpl(Connection conn) {
        this.conn = conn;
        this.bookRepo = new RepositoryBookImpl(conn);
        this.authorRepo = new RepositoryAuthorImpl(conn);
    }

    @Override
    public List<Book> executeGetAllBooks() {
        return bookRepo.executeGetAll();
    }

    @Override
    public List<Author> executeGetAllAuthors() {
        return authorRepo.executeGetAll();
    }

    @Override
    public void executePut(Book book) {
        try {
            Author newAuthor = this.executeGetByName(book.getAuthor().getName());
            if (newAuthor.getAuthorId() == 0){
                newAuthor = authorRepo.executePut(newAuthor);
            }
            book.setAuthor(newAuthor);
            bookRepo.executePut(book);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }

    }

    @Override
    public Optional<Book> executeGetByTitle(String title) {
        return bookRepo.executeByTitle(title);
    }

    @Override
    public Author executeGetByName(String name) {
        return authorRepo.executeGetByName(name);
    }
}
