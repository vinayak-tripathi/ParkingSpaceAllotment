/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    public String out(Connection con,String table, String qry) {
        DateFormat TimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	Date timeOut = new Date();
	String timeout = (TimeFormat.format(timeOut));
        System.out.println(timeout);
        double cost;
        try{
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
            System.out.print("TimeIn");
            System.out.print("TimeOUT");
            System.out.println(timein+"  "+timeout);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime to= LocalDateTime.parse(timeout, formatter2);
            LocalDateTime ti= LocalDateTime.parse(timein, formatter2);
            long hours = java.time.Duration.between(ti, to).toHours();
            long diffInMinutes = java.time.Duration.between(ti, to).toMinutes();
            if (diffInMinutes > hours*60) 
                hours = 1+hours;
            
            cost = calculateCharges(hours,type);
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO history(vehicleNo,name,phone,slot,type,timein,timeout,charge) values('"+veh+"','"+nam+"',"+phone+","+sl+",'"+type+"','"+timein+"','"+timeout+"',"+cost+")");
            st.executeUpdate("DELETE from "+table+" "+qry);
            st.executeUpdate("INSERT INTO available"+table+"(slot) values("+rs.getInt("slot")+")");
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error!", JOptionPane.ERROR_MESSAGE);
        }
        return timeout;
    }
    
    public ResultSet his(Connection con){
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery("Select * FROM history");
            System.out.println(rs);
            System.out.println("it Runs");
        }
        catch(SQLException e){
            rs= null;
            System.out.println(e);
        }
        return rs;
    }       
    public ResultSet his(Connection con,String val){
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery("Select * FROM history WHERE type =  '"+val+"'");
            System.out.println(rs);
            System.out.println("it Runs");
        }
        catch(SQLException e){
            rs= null;
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet his(Connection con, String match,String val){
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery("Select * FROM history WHERE "+match+" = '"+val+"'");
            System.out.println(rs);
            System.out.println("it Runs");
        }
        catch(SQLException e){
            rs= null;
            System.out.println(e);
        }
        return rs;
    }
    
    public static double calculateCharges (double numHours, String ty){

        double prices = 0;

        if(numHours<=1 && "TwoWheeler".equals(ty))
            prices = 10;

        else if(numHours>1 && numHours<=100 && "TwoWheeler".equals(ty))
            prices = 10.0 + 5.0*(numHours - 1);

        else if (numHours >100 && "TwoWheeler".equals(ty))
            prices = 1000;
        
        else if(numHours<=1 && "FourWheeler".equals(ty))
            prices = 15;
        
        else if(numHours>1 && numHours<=100 && "FourWheeler".equals(ty))
            prices = 15.0 + 10.0*(numHours - 1);
        
        else if (numHours >100 && "FourWheeler".equals(ty))
            prices = 2000;

        return prices;
              
    }
}

