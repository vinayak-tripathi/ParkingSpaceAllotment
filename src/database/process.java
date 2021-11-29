/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import database.connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author Vinayak
 */
public class process extends connect{
    
    public void bookSlot(Connection con,String table,String veh, String name, Long pho, int slt){
        
        DateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss a");
	Date time = new Date();
	String Time = (TimeFormat.format(time));
        System.out.println(Time);
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
        System.out.println("From Database");
        System.out.println("Values\n "+veh+"\n "+name+"\n "+pho + "\n "+slt);
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select COUNT(*) from available"+table+ " where slot="+ slt);
            //System.out.println();
            if(rs.getInt("COUNT(*)")!=0){
                st.executeUpdate("INSERT into "+table+"(vehicleNo,name,pho,slot,timein)VALUES('"+veh+"','"+name+"','"+pho+"','"+slt+"','"+date+" "+Time+"')");
                st.executeUpdate("DELETE from available"+table+" where slot="+slt);
                System.out.println("Booked");
            }
            else{
                JOptionPane.showMessageDialog(null,"Slot number exceeded","Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error!", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    public ResultSet availslot(Connection con,String table){
        
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery("Select * FROM "+table);
            System.out.println("it Runs");
            System.out.println("Done All good");
       }
        catch(Exception e){
            rs =null;
            System.out.println(e);
        }
        return rs;
    }
    public void out(Connection con,String qry) {
        System.out.println("Delete from Two "+qry);
    }
}
