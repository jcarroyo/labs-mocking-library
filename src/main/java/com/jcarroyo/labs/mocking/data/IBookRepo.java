package com.jcarroyo.labs.mocking.data;

import java.util.ArrayList;

public interface IBookRepo {
    public void insertBook(String bookName, String flatName, float price) throws Exception;
    public boolean lookForDuplicated(String flatName) throws Exception;
    public ArrayList<Book> getAllBooks() throws Exception;
    public void closeConnection();
    public void auditAction();
    public Book findBook(String bookName) throws Exception;
}
