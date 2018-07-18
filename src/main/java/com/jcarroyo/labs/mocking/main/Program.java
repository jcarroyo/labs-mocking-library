package com.jcarroyo.labs.mocking.main;

import com.jcarroyo.labs.mocking.data.BookRepo;
import com.jcarroyo.labs.mocking.logic.BookLogic;

public class Program {
    public static void main(String[] args){
        try{
            BookLogic bookLogic = new BookLogic();
            bookLogic.registerBook("Fulanito", 13.55f);
            bookLogic.printBooks();
            bookLogic.closeLibraryRepo();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
