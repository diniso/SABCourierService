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
import rs.etf.sab.operations.CourierRequestOperation;

/**
 *
 * @author vulov
 */
public class vv180421d_CourierRequestOperation implements CourierRequestOperation{

    @Override
    public boolean insertCourierRequest(String userName, String driverLicenceNumber) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @username varchar(100);\n" +
"declare @vozackaDozvola varchar(100);\n" +
"declare @shouldInsert BIT;\n" +
"set @username = ?;\n" +
"set @vozackaDozvola = ?;\n" +
"set @shouldInsert=  (select count(*) from Kurir where KorisnickoIme = @username);\n" +
"if @shouldInsert = 0\n" +
"begin\n" +
"	insert into PostaniKurir(KorisnickoIme,BrojVozackeDozvole) values(@username,@vozackaDozvola)\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, userName);
            ps.setString(2, driverLicenceNumber);
            if (ps.executeUpdate() == 1) return true;

        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CourierRequestOperation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean deleteCourierRequest(String userName) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="delete from PostaniKurir where KorisnickoIme = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, userName);
            if (ps.executeUpdate() == 1) return true;

        } catch (SQLException ex) {
       //     java.util.logging.Logger.getLogger(vv180421d_CourierRequestOperation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean changeDriverLicenceNumberInCourierRequest(String userName, String licencePlateNumber) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="update PostaniKurir set BrojVozackeDozvole = ? where KorisnickoIme = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, licencePlateNumber);
            ps.setString(2, userName);
            if (ps.executeUpdate() == 1) return true;

        } catch (SQLException ex) {
       //    java.util.logging.Logger.getLogger(vv180421d_CourierRequestOperation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<String> getAllCourierRequests() {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select KorisnickoIme from PostaniKurir";
        try (PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){

            List<String> usernames = new ArrayList<>();
            while (rs.next()) usernames.add(rs.getString("KorisnickoIme"));
            
            return usernames;
            
        } catch (SQLException ex) {
       //     java.util.logging.Logger.getLogger(vv180421d_CourierRequestOperation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public boolean grantRequest(String username) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @vozackaDozvola varchar(100);\n" +
"declare @username varchar(100);\n" +
"set @username = ?;\n" +
"set @vozackaDozvola = (select top 1 BrojVozackeDozvole from PostaniKurir where KorisnickoIme = @username);\n" +
"if @vozackaDozvola is not NULL\n" +
"begin\n" +
"	insert into Kurir(KorisnickoIme,BrojVozackeDozvole) values(@username, @vozackaDozvola);\n" +
"	delete from PostaniKurir where KorisnickoIme = @username;\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, username);
            if (ps.executeUpdate() > 0) return true;

        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CourierRequestOperation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
