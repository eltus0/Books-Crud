package org.david.bookscrud;

import org.david.bookscrud.connection.GetConnetionSingleton;
import org.david.bookscrud.crud.service.IService;
import org.david.bookscrud.crud.service.ServiseGeneralImpl;
import org.david.bookscrud.models.Credentials;
import org.david.bookscrud.models.dtos.Author;
import org.david.bookscrud.models.dtos.Book;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IService service = new ServiseGeneralImpl(GetConnetionSingleton.getInstance().getConnection(new Credentials()));
        Scanner sc = new Scanner(System.in);

        showAllBooks(service);

        System.out.println("Do you want to do?");
        System.out.println("1 insert");
        System.out.println("2 update");

        int option = 0;
        while (option <= 0) {
            try {
                option = Integer.parseInt(sc.nextLine());
                if (option < 0 || option > 2) {
                    System.err.println("Option no valid");
                    option = 0;
                }
            } catch (NumberFormatException ex) {
                System.err.println("Its not number, try again");
                option = 0;
            }
        }


        if (option == 1) {
            Book book = new Book();
            System.out.print("Title book: ");
            String title = sc.nextLine();
            Optional<Book> book1 = service.executeGetByTitle(title);
            if (book1.isPresent()){
                System.err.println("Ese libro ya existe");
                System.exit(0);
            }
            book.setTitle(title);

            System.out.print("Author name: ");
            String authorName = sc.nextLine();
            book.setAuthor(new Author(0, authorName));

            book = status(sc, book);
            service.executePut(book);
            showAllBooks(service);
        } else {
            System.out.print("Title book: ");
            String bookTitle = sc.nextLine();
            Optional<Book> bookOptional = service.executeGetByTitle(bookTitle);
            if (bookOptional.isEmpty()){
                System.err.println("No existe ese libro");
                System.exit(0);
            }
            Book book = bookOptional.get();
            System.out.println(book);
            System.out.println("Title: ");
            String title = sc.nextLine();
            System.out.println("Status: ");
            book = status(sc, book);
            System.out.println("Reflex: ");
            String reflex = sc.nextLine();

            book.setTitle(title);
            book.setReflex(reflex);

            service.executePut(book);
            showAllBooks(service);
        }
        
    }

    private static void showAllBooks(IService service){
        service.executeGetAllBooks()
                .forEach(a -> System.out.println(a.getTitle() + " | " + a.getStatus() + " | " + a.getAuthor().getName()));
    }

    private static Book status(Scanner sc, Book book) {
        System.out.println("1 Deseo");
        System.out.println("2 por comprar");
        System.out.println("3 get");
        System.out.println("4 leiendo");
        System.out.println("5 pausa");
        System.out.println("6 leido");
        System.out.println("7 analizando");
        System.out.println("8 en practica");
        System.out.println("9 anotado/finish");
        Integer statusNum;
        do {
            try {
                statusNum = Integer.parseInt(sc.nextLine());
                if (statusNum <= 0 || statusNum > 9) {
                    System.err.println("Its not option, try again");
                }
            } catch (NumberFormatException ex) {
                System.err.println("Its not a number");
                statusNum = 0;
            }
        } while (statusNum <= 0 || statusNum > 9);

        switch (statusNum) {
            case 1 -> book.setStatus("Deseo");
            case 2 -> book.setStatus("por comprar");
            case 3 -> book.setStatus("get");
            case 4 -> book.setStatus("leiendo");
            case 5 -> book.setStatus("pausa");
            case 6 -> book.setStatus("leido");
            case 7 -> book.setStatus("analizando");
            case 8 -> book.setStatus("en practica");
            case 9 -> book.setStatus("anotado/finish");
        }
        return book;
    }
}
