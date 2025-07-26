package SYSTEM;

import java.sql.*;

public class Conn implements AutoCloseable {
    Connection c;
    Statement s;
    public Conn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c =DriverManager.getConnection("jdbc:mysql:///hotelManagementSystem","root","Ronal@2626");
            s =c.createStatement();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {

    }
}