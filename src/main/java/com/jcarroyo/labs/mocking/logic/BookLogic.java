package com.jcarroyo.labs.mocking.logic;

import com.jcarroyo.labs.mocking.data.Book;
import com.jcarroyo.labs.mocking.data.BookRepo;
import com.jcarroyo.labs.mocking.data.IBookRepo;

import java.util.ArrayList;

public class BookLogic {
    IBookRepo bookRepo;

    public BookLogic(IBookRepo bookr){
        this.bookRepo = bookr;
    }

    public BookLogic() throws Exception{
        bookRepo = new BookRepo();
    }

    public Book registerBook(String bookName, float price) throws Exception{
        if(bookRepo.lookForDuplicated(bookName)){
            throw new DuplicatedBookException();
        }
        String flatName = applyFlatFormat(bookName);
        float priceFormatted = applyRoundFormat(price);
        bookRepo.insertBook(bookName, flatName, priceFormatted);

        Book book = new Book();
        book.setName(bookName);
        book.setFlatName(flatName);
        book.setPrice(priceFormatted);
        return book;
    }

    private String applyFlatFormat(String bookName){
        //"Alicia en el país de las maravillas" -> "alicia en el pais de las maravillas"
        return bookName
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("ñ", "n")
                .toLowerCase();
    }

    private float applyRoundFormat(float price) {
        return Math.round(price);
    }

    public void printBooks() throws Exception{
        ArrayList<Book> books = bookRepo.getAllBooks();
        bookRepo.auditAction();
        if(books == null){
            throw new LibraryEmptyException();
        }
        System.out.println("---La libreria tiene los siguientes libros---");
        for(Book book : books){
            int id = book.getId();
            String flatName = book.getFlatName();
            float price = book.getPrice();

            System.out.println("ID:" + Integer.toString(id) + "\t" +
                    "BOOK:" + flatName + "\t" + "PRICE:" + Float.toString(price));
        }
    }

    public void closeLibraryRepo(){
        bookRepo.closeConnection();
    }

    public class DuplicatedBookException extends Exception
    {

    }

    public class LibraryEmptyException extends Exception
    {

    }

}
