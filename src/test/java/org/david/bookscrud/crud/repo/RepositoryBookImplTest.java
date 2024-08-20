package org.david.bookscrud.crud.repo;

import org.david.bookscrud.connection.GetConnetionSingleton;
import org.david.bookscrud.models.Credentials;
import org.david.bookscrud.models.dtos.Author;
import org.david.bookscrud.models.dtos.Book;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryBookImplTest {

    private IRepositoryBook repo = new RepositoryBookImpl(GetConnetionSingleton.getInstance().getConnection(new Credentials()));
    private IRepositoryAuthor repoa = new RepositoryAuthorImpl(GetConnetionSingleton.getInstance().getConnection(new Credentials()));

    @Test
    void executeGetAll() {
        List<Book> books = repo.executeGetAll();

        books.forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings = {"david", "juan"})
    void executeGetNameAuthor(String name){
        List<Book> books = repo.executeByAuthor(name);
        books.forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings = {"por comprar", "get"})
    void executeGetCategory(String category){
        List<Book> books = repo.executeByStatus(category);
        books.forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings = {"la vida", "jajaj", "fafbfgfvfv", "sasas", "iqiqiq"})
    void executeGetTitle(String title){
        System.out.println(repo.executeByTitle(title));
    }

    @Test
    void insert (){
        Book book = new Book();
        book.setStatus("get");
        book.setTitle("el");
        book.setAuthor(new Author(5, "luis"));
        repo.executePut(book);
        repo.executeGetAll().forEach(System.out::println);
    }

    @Test
    void insertauthor (){
        Author author = new Author();
        author.setAuthorId(1);
        author.setName("david");
        author= repoa.executePut(author);
        System.out.println(author);
        repoa.executeGetAll().forEach(System.out::println);
    }


}
