/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Vinayak
 */
public class connect {
    public static Connection setConnection(){
        String url = "jdbc:sqlite:parking.sqlite";
        Connection con = null;
        try
        {
            con = DriverManager.getConnection(url);
            System.out.println("Connection Established Successfull");
        }
        catch(SQLException sql)
        {
            System.out.println(sql);
        }
       return con; 
    }
    
}
