package com.jcarroyo.labs.mocking.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;

public class BookRepo implements IBookRepo {
    private String dbUrl = "jdbc:derby:Library;create=true;";
    private String table = "Book";

    private Connection conn = null;
    private Statement stmt = null;

    private static final Logger LOGGER = Logger.getLogger( "BookRepo" );

    public BookRepo() throws Exception{
        try{
            createConnection();
            initialize();
        }
        catch(Exception ex){
            throw ex;
        }
    }

    private void createConnection() throws Exception
    {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        System.setProperty("derby.system.home", "src/main/resources/.derby");
        conn = DriverManager.getConnection(dbUrl);
    }

    private void initialize() throws Exception{
        Statement st = conn.createStatement();
        try{
            st.executeUpdate("DROP TABLE BOOK");
        }
        catch (Exception ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        st.executeUpdate("CREATE TABLE BOOK(ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                + "NAME VARCHAR(30), FLATNAME VARCHAR(30), PRICE FLOAT)");
    }

    public boolean lookForDuplicated(String flatName) throws Exception{
        boolean duplicated = false;
        PreparedStatement stm = conn.prepareStatement(
                "SELECT * FROM BOOK WHERE FLATNAME LIKE ?"
        );
        stm.setString(1, flatName);
        ResultSet resultSet = stm.executeQuery();
        while(resultSet.next()){
            duplicated = true;
        }
        stm.close();
        return duplicated;
    }

    public void insertBook(String bookName, String flatName, float price) throws Exception{
        PreparedStatement stm = conn.prepareStatement(
                "INSERT INTO BOOK(NAME, FLATNAME, PRICE) VALUES (?, ?, ?)"
        );
        stm.setString(1, bookName);
        stm.setString(2, flatName);
        stm.setFloat(3, price);
        stm.executeUpdate();
    }

    public ArrayList<Book> getAllBooks() throws Exception{
        ArrayList<Book> books = null;

        String query = "SELECT ID, FLATNAME, PRICE FROM BOOK";
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery(query);
        while(resultSet.next()){
            int id = resultSet.getInt("ID");
            String flatName = resultSet.getString("FLATNAME");
            float price = resultSet.getFloat("PRICE");

            if(books == null){
                books = new ArrayList<Book>();
            }
            Book book = new Book();
            book.setId(id);
            book.setFlatName(flatName);
            book.setPrice(price);
            books.add(book);
        }
        stm.close();

        return books;
    }

    public void closeConnection(){
        try{
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }
        catch (SQLException ex){
            if(ex.getErrorCode() == 50000 && ex.getSQLState().equals("XJ015")){
                System.out.println("BD Cerrada");
            }
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public void auditAction() {
        throw new NotImplementedException();
    }

    public Book findBook(String flatName) throws Exception {
        //IMPLEMENTAR ESTE METODO
        throw new NotImplementedException();
    }
}


