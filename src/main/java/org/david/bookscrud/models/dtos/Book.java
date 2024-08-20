package org.david.bookscrud.models.dtos;

import java.util.Objects;

public class Book {

    private Integer bookId;
    private String title;
    private Author author;
    private String status;
    private String reflex;

    public Book() {
    }

    public Book(Integer bookId, String title, Author author, String status, String reflex) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.status = status;
        this.reflex = reflex;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReflex() {
        return reflex;
    }

    public void setReflex(String reflex) {
        this.reflex = reflex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && status == book.status && Objects.equals(reflex, book.reflex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, status, reflex);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", status='" + status + '\'' +
                ", reflex='" + reflex + '\'' +
                '}';
    }
}
