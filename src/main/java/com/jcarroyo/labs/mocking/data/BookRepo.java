package com.jcarroyo.labs.mocking.data;

import java.sql.*;

public class BookRepo {
    private String dbUrl = "jdbc:derby:Library;create=true;";
    private String table = "Book";

    private Connection conn = null;
    private Statement stmt = null;

    public BookRepo() throws Exception{
        try{
            createConnection();
            initialize();
        }
        catch(Exception ex){
            throw ex;
        }
    }

    public void close() throws Exception{
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

    private void createConnection() throws Exception
    {
        System.setProperty("derby.system.home", "src/main/resources/.derby");
        conn = DriverManager.getConnection(dbUrl);
    }

    private void initialize() throws Exception{
        Statement st = conn.createStatement();
        st.executeUpdate("CREATE TABLE BOOK(ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                + "NAME VARCHAR(30), PRICE INT)");
        st.executeUpdate("INSERT INTO BOOK(NAME, PRICE) VALUES('Libro1', 40.20)");
        st.executeUpdate("INSERT INTO BOOK(NAME, PRICE) VALUES('Libro2', 50.30)");
        st.executeUpdate("INSERT INTO BOOK(NAME, PRICE) VALUES('Libro3', 20.99)");
        st.executeUpdate("INSERT INTO BOOK(NAME, PRICE) VALUES('Libro4', 11.46)");
    }

    public void insertBook(String bookName, long price) throws Exception{
        PreparedStatement stm = conn.prepareStatement(
        "INSERT INTO BOOK(NAME, PRICE) VALUES (?, ?)"
        );
        stm.setString(1, bookName);
        stm.setLong(2, price);
        stm.executeUpdate();
    }

    public void printBooks() throws Exception{
        String query = "SELECT ID, NAME, PRICE FROM BOOK";
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery(query);
        while(resultSet.next()){
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            long price = resultSet.getLong("PRICE");

            System.out.println("ID:" + Integer.toString(id) + "\t" +
            "BOOK:" + name + "\t" + "PRICE:" + Long.toString(price));
        }
        stm.close();
    }
}
