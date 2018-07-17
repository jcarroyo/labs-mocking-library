package com.jcarroyo.labs.mocking.main;

import com.jcarroyo.labs.mocking.data.BookRepo;

public class Program {
    public static void main(String[] args){
        try{
            BookRepo bookRepo = new BookRepo();
            long a = (long)13.99;
            bookRepo.printBooks();
            bookRepo.insertBook("BUKI", a);
            bookRepo.printBooks();
            bookRepo.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
