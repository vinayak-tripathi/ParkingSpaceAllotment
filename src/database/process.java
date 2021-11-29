/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import database.connect;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author Vinayak
 */
public class process extends connect{
    
    public void bookSlot(Connection con,String table,String veh, String name, Long pho, int slt){
        
        DateFormat TimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	Date time = new Date();
	String Time = (TimeFormat.format(time));
        System.out.println(Time);
        
        System.out.println("From Database");
        System.out.println("Values\n "+veh+"\n "+name+"\n "+pho + "\n "+slt);
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select COUNT(*) from available"+table+ " where slot="+ slt);
            //System.out.println();
            if(rs.getInt("COUNT(*)")!=0){
                st.executeUpdate("INSERT into "+table+"(vehicleNo,name,pho,slot,timein)VALUES('"+veh+"','"+name+"','"+pho+"','"+slt+"','"+Time+"')");
                st.executeUpdate("DELETE from available"+table+" where slot="+slt);
                System.out.println("Booked");
            }
            else{
                JOptionPane.showMessageDialog(null,"Slot number exceeded","Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(HeadlessException | SQLException e){
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
        catch(SQLException e){
            rs =null;
            System.out.println(e);
        }
        return rs;
    }
    
    public void out(Connection con,String table, String qry) {
        DateFormat TimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	Date timeOut = new Date();
	String timeout = (TimeFormat.format(timeOut));
        System.out.println(timeout);
        try{
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            ResultSet rs = st1.executeQuery("Select * from "+table+" "+qry);
            int sl = rs.getInt("slot");
            String veh = rs.getString("vehicleNo");
            String nam = rs.getString("name");
            Long phone = rs.getLong("pho");
            String type = table+"Wheeler";
            String timein = rs.getString("timein");
            
            System.out.println("Delete from "+table+" "+qry);
            System.out.println("Select slot from "+table+" "+qry);
            System.out.println("DELETE from "+table+" "+qry);
            System.out.println("INSERT INTO available"+table+"(slot) values("+sl+")");
            st.executeUpdate("INSERT INTO history(vehicleNo,name,phone,slot,type,timein,timeout,charge) values('"+veh+"','"+nam+"',"+phone+","+sl+",'"+type+"','"+timein+"','"+timeout+"',"+12+")");
            st.executeUpdate("DELETE from "+table+" "+qry);
            st.executeUpdate("INSERT INTO available"+table+"(slot) values("+rs.getInt("slot")+")");
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error!", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
