/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rs.etf.sab.operations.CourierOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_CourierOperations implements CourierOperations{

    @Override
    public boolean insertCourier(String courierUserName, String driverLicenceNumber) {
          Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="insert into Kurir(KorisnickoIme,BrojVozackeDozvole) values(?,?)";
        try (PreparedStatement ps=conn.prepareStatement(query)){
                
            ps.setString(1, courierUserName);
            ps.setString(2, driverLicenceNumber);
            
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(vv180421d_CourierOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean deleteCourier(String courierUserName) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="delete from Kurir where KorisnickoIme = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){
                
            ps.setString(1, courierUserName);
            
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CourierOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<String> getCouriersWithStatus(int statusOfCourier) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select KorisnickoIme from Kurir where [Status] = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){
                
            ps.setInt(1, statusOfCourier);
            ResultSet rs = ps.executeQuery();
            List<String> usernames = new ArrayList<>();
            while (rs.next())
                usernames.add(rs.getString("KorisnickoIme"));
            
            rs.close();
            return usernames;

        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CourierOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public List<String> getAllCouriers() {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select KorisnickoIme from Kurir";
        try (PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){
                
            List<String> usernames = new ArrayList<>();
            while (rs.next())
                usernames.add(rs.getString("KorisnickoIme"));
            
            return usernames;

        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CourierOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public BigDecimal getAverageCourierProfit(int numberOfDeliveries) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return BigDecimal.valueOf(0);
        
        if (numberOfDeliveries == -1) {
            
            String query="select coalesce(avg(OstvareniProfit), 0.0) as Prosecno from Kurir";
            try (PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){
                
                if (rs.next()) return BigDecimal.valueOf(rs.getFloat("Prosecno"));
            

            } catch (SQLException ex) {
             //    java.util.logging.Logger.getLogger(vv180421d_CourierOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            
            return BigDecimal.valueOf(0);
        }
        
        
        String query="select coalesce(avg(OstvareniProfit), 0.0) as Prosecno from Kurir where BrojIsporucenihPaketa = ?";
            try (PreparedStatement ps=conn.prepareStatement(query);){
                
                ps.setInt(1, numberOfDeliveries);
                ResultSet rs = ps.executeQuery();
                float prosecno = 0;
                if (rs.next()) prosecno = rs.getFloat("Prosecno");

                rs.close();
                
                return BigDecimal.valueOf(prosecno);

            } catch (SQLException ex) {
             //    java.util.logging.Logger.getLogger(vv180421d_CourierOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            
            return BigDecimal.valueOf(0);
    }
    
}
