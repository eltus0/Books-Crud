package org.david.bookscrud.crud.service;

import org.david.bookscrud.crud.repo.IRepositoryAuthor;
import org.david.bookscrud.crud.repo.IRepositoryBook;
import org.david.bookscrud.models.dtos.Author;
import org.david.bookscrud.models.dtos.Book;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IService {

    List<Book> executeGetAllBooks();

    List<Author> executeGetAllAuthors();

    void executePut(Book book);

    Optional<Book> executeGetByTitle(String title);

    Author executeGetByName(String name);


}
