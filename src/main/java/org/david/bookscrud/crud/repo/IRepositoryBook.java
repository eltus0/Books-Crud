package org.david.bookscrud.crud.repo;

import org.david.bookscrud.models.dtos.Book;

import java.util.List;
import java.util.Optional;

public interface IRepositoryBook {

    List<Book> executeByAuthor(String nameAuthor);

    List<Book> executeByStatus(String status);

    Optional<Book> executeByTitle(String title);

    List<Book> executeGetAll();

    void executePut(Book book);
}
