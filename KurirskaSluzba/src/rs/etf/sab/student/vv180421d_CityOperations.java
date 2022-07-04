/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rs.etf.sab.operations.CityOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_CityOperations implements CityOperations{

    @Override
    public int insertCity(String name, String postalCode) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -1;
        
        String query="insert into Grad([Naziv],[PostanskiBroj]) values(?,?)";
        try (PreparedStatement ps=conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){

            ps.setString(1, name);
            ps.setString(2, postalCode);
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next())
                return rs.getInt(1);
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_CityOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public int deleteCity(String... names) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return 0;
        
        String query="delete from Grad\n" +
                "where Naziv in ";
        
        int i = 0 ;
        for (String name: names) {
            if (i++ == 0) query += "(?";
            else query += ",?";
        }
        
        query += ")";
        
        try (PreparedStatement ps=conn.prepareStatement(query);){
            i = 1;
            for (String name: names) {
               ps.setString(i++, name);
            }
            return ps.executeUpdate();
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CityOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return 0;
    }

    @Override
    public boolean deleteCity(int idCity) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="delete from Grad\n" +
                "where IdGrada = ?";
        try (PreparedStatement ps=conn.prepareStatement(query);){

            ps.setInt(1, idCity);
            if (ps.executeUpdate() == 1) return true;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CityOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<Integer> getAllCities() {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select IdGrada \n" +
                    "from Grad";
        try (PreparedStatement ps=conn.prepareStatement(query);
               ResultSet rs = ps.executeQuery();){

            List<Integer> ids = new ArrayList<>();
            while(rs.next()) {
                ids.add(rs.getInt("IdGrada"));
            }
                
            return ids;        
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CityOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return  new ArrayList<>();
    }
    
}
