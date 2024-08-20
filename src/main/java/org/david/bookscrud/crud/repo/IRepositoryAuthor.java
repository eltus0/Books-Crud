package org.david.bookscrud.crud.repo;

import org.david.bookscrud.models.dtos.Author;

import java.util.List;
import java.util.Optional;

public interface IRepositoryAuthor {

    List<Author> executeGetAll();

    Author executeGetByName(String name);

    Author executePut(Author author);

}
