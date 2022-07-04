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
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_VehicleOperations implements VehicleOperations{

    @Override
    public boolean insertVehicle(String licencePlateNumber, int fuelType, BigDecimal fuelConsumtion, BigDecimal capacity) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="insert into Vozilo(RegistracioniBroj,TipPotrosnje,PotrosnjaPoKm,Nosivost) values(?,?,?,?)";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, licencePlateNumber);
            ps.setInt(2, fuelType);
            ps.setDouble(3, fuelConsumtion.doubleValue());
            ps.setDouble(4, capacity.doubleValue());
            
            if (ps.executeUpdate() > 0) return true;

            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_VehicleOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public int deleteVehicles(String... licencePlateNumbers) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return 0;
        
        String query="delete from Vozilo\n" +
                "where RegistracioniBroj in ";
        
        int i = 0 ;
        for (String name: licencePlateNumbers) {
            if (i++ == 0) query += "(?";
            else query += ",?";
        }
        
        query += ")";
        
        try (PreparedStatement ps=conn.prepareStatement(query);){
            i = 1;
            for (String name: licencePlateNumbers) {
               ps.setString(i++, name);
            }
            return ps.executeUpdate();
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_VehicleOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return 0;
    }

    @Override
    public List<String> getAllVehichles() {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select RegistracioniBroj from Vozilo";
        try (PreparedStatement ps=conn.prepareStatement(query);
               ResultSet rs = ps.executeQuery();){

            List<String> registracioniBrojevi = new ArrayList<>();
            while(rs.next()) {
                registracioniBrojevi.add(rs.getString("RegistracioniBroj"));
            }
                
            return registracioniBrojevi;        
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_CityOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return  new ArrayList<>();
    }

    @Override
    public boolean changeFuelType(String licencePlateNumber, int fuelType) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @registracioniBroj varchar(100);\n" +
"declare @tipPostosnje int\n" +
"set @registracioniBroj = ?;\n" +
"set @tipPostosnje = ?;\n" +
"if (select count(*) from VoziloUMagacinu where RegistracioniBroj = @registracioniBroj) = 1\n" +
"begin \n" +
"	update Vozilo set TipPotrosnje = @tipPostosnje where RegistracioniBroj = @registracioniBroj\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, licencePlateNumber);
            ps.setInt(2, fuelType);
            
            if (ps.executeUpdate() > 0) return true;

            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_VehicleOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean changeConsumption(String licencePlateNumber, BigDecimal fuelConsumption) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @registracioniBroj varchar(100);\n" +
"declare @postosnja decimal(10,3)\n" +
"set @registracioniBroj = ?;\n" +
"set @postosnja = ?;\n" +
"if (select count(*) from VoziloUMagacinu where RegistracioniBroj = @registracioniBroj) = 1\n" +
"begin \n" +
"	update Vozilo set PotrosnjaPoKm = @postosnja where RegistracioniBroj = @registracioniBroj\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, licencePlateNumber);
            ps.setFloat(2, fuelConsumption.floatValue());
            
            if (ps.executeUpdate() > 0) return true;

            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_VehicleOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean changeCapacity(String licencePlateNumber, BigDecimal capacity) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @registracioniBroj varchar(100);\n" +
"declare @nosivost decimal(10,3)\n" +
"set @registracioniBroj = ?;\n" +
"set @nosivost = ?;\n" +
"if (select count(*) from VoziloUMagacinu where RegistracioniBroj = @registracioniBroj) = 1\n" +
"begin \n" +
"	update Vozilo set Nosivost = @nosivost where RegistracioniBroj = @registracioniBroj\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, licencePlateNumber);
            ps.setFloat(2, capacity.floatValue());
            
            if (ps.executeUpdate() > 0) return true;

            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_VehicleOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean parkVehicle(String licencePlateNumber, int idStockroom) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @registracioniBroj varchar(100);\n" +
"declare @idMagacina int;\n" +
"set @registracioniBroj = ?;\n" +
"set @idMagacina = ?;\n" +
"if (select top 1 KorisnickoImeVozacaKojiVozi from Vozilo where RegistracioniBroj = @registracioniBroj) is null\n" +
"begin\n" +
"	insert into VoziloUMagacinu(RegistracioniBroj,IdAdrese) values(@registracioniBroj,@idMagacina)\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, licencePlateNumber);
            ps.setInt(2, idStockroom);
            
            if (ps.executeUpdate() > 0) return true;

            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_VehicleOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
