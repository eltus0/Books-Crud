package org.david.bookscrud.crud.repo;

import org.david.bookscrud.models.dtos.Author;
import org.david.bookscrud.models.dtos.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryAuthorImpl implements IRepositoryAuthor{

    private Connection conn;

    public RepositoryAuthorImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Author> executeGetAll() {
        List<Author> authors = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement("select * from authors")){
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                authors.add(createAuthor(rs));
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return authors;
    }

    @Override
    public Author executeGetByName(String name) {
        Author author = new Author(0, name);
        try (PreparedStatement pre = conn.prepareStatement("select * from authors where name = ?")){
            pre.setString(1, name);
            ResultSet rs = pre.executeQuery();
            if (rs.next()){
                author = createAuthor(rs);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return author;
    }

    @Override
    public Author executePut(Author author) {
        String query = "";
        if (author.getAuthorId() != null && author.getAuthorId() > 0) {
            query = "update authors set name = ? where name=?";
        } else {
            query = "insert into authors (name) values (?)";
        }

        try (PreparedStatement pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            pre.setString(1, author.getName());
            if (author.getAuthorId() != null && author.getAuthorId() > 0) {
                pre.setString(2, author.getName());
                pre.executeUpdate();
            } else {
                pre.executeUpdate();
                ResultSet rs = pre.getGeneratedKeys();
                if (rs.next()){
                    author.setAuthorId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.out.println("asdasdas");
        }
        return author;
    }

    private Author createAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getInt("author_id"));
        author.setName(rs.getString("name"));
        return author;
    }
}
