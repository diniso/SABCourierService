/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import rs.etf.sab.operations.AddressOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_AdressOperations implements AddressOperations {

    @Override
    public int insertAddress(String street, int number, int cityId, int xCord, int yCord) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -1;
        
        String query="insert into Adresa(Ulica, Broj, IdGrada , X , Y) values(?, ?, ?, ? , ?)";
        try (PreparedStatement ps=conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){

            ps.setString(1, street);
            ps.setInt(2, number);
            ps.setInt(3, cityId);
            ps.setInt(4, xCord);
            ps.setInt(5, yCord);
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next())
                return rs.getInt(1); 
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_AdressOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public int deleteAddresses(String street, int number) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return 0;
        
        String query="delete from Adresa where Ulica = ? and Broj = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, street);
            ps.setInt(2, number);
            return ps.executeUpdate();
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_AdressOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return 0;
    }

    @Override
    public boolean deleteAdress(int idAddress) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="delete from Adresa where IdAdrese = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, idAddress);
            return (ps.executeUpdate() == 1);
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_AdressOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public int deleteAllAddressesFromCity(int idCity) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return 0;
        
        String query="delete from Adresa where IdGrada = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, idCity);
            return ps.executeUpdate();
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_AdressOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return 0;
    }

    @Override
    public List<Integer> getAllAddresses() {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select IdAdrese from Adresa";
        try (PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){

            List<Integer> ids = new ArrayList<>();
            while (rs.next())
                ids.add(rs.getInt("IdAdrese"));
            
            return ids;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_AdressOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getAllAddressesFromCity(int idCity) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query2="select Naziv from Grad where IdGrada = ?";
        try (PreparedStatement ps=conn.prepareStatement(query2)){

            ps.setInt(1 , idCity);   
            ResultSet rs = ps.executeQuery();
            List<Integer> ids = new ArrayList<>();
            if (!rs.next()) return null;

            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_AdressOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        String query="select IdAdrese from Adresa where IdGrada = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1 , idCity);   
            ResultSet rs = ps.executeQuery();
            List<Integer> ids = new ArrayList<>();
            while (rs.next())
                ids.add(rs.getInt("IdAdrese"));
            
            return ids;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_AdressOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }
    
}
