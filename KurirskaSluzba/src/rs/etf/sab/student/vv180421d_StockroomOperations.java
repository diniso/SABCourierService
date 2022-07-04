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
import rs.etf.sab.operations.StockroomOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_StockroomOperations implements StockroomOperations{

    @Override
    public int insertStockroom(int adress) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -1;
        
        String query="declare @idAdress int;\n" +
"declare @idGrada int;\n" +
"declare @brojGradova int;\n" +
"set @idAdress = ?;\n" +
"select top 1 @idGrada = IdGrada from Adresa where IdAdrese = @idAdress;\n" +
"if @idGrada is not null\n" +
"begin\n" +
"	select @brojGradova = count(*) from LokacijaMagacina lm join Adresa a on lm.IdAdrese = a.IdAdrese where a.IdGrada = @idGrada;\n" +
"	if @brojGradova = 0\n" +
"	begin\n" +
"		insert into LokacijaMagacina values(@idAdress)\n" +
"	end\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, adress);
            if (ps.executeUpdate() > 0) return adress;

            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_StockroomOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public boolean deleteStockroom(int idStockroom) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="delete from LokacijaMagacina where IdAdrese = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, idStockroom);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_StockroomOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public int deleteStockroomFromCity(int idCity) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -1;
        
        String query="declare @idMagacina int;\n" +
"declare @idCity int;\n" +
"set @idCity = ?;\n" +
"select top 1 @idMagacina = lm.IdAdrese from LokacijaMagacina lm join Adresa a on lm.IdAdrese = a.IdAdrese where a.IdGrada = @idCity\n" +
"if @idMagacina is not null\n" +
"begin\n" +
"	delete from LokacijaMagacina where IdAdrese = @idMagacina;\n" +
"	select @idMagacina as IdMagacina\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, idCity);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next())
                return rs.getInt("IdMagacina");
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_StockroomOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public List<Integer> getAllStockrooms() {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="SELECT IdAdrese\n" +
"FROM LokacijaMagacina";
        try (PreparedStatement ps=conn.prepareStatement(query);
               ResultSet rs = ps.executeQuery();){

            List<Integer> ids = new ArrayList<>();
            while(rs.next()) {
                ids.add(rs.getInt("IdAdrese"));
            }
                
            return ids;        
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_StockroomOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return  new ArrayList<>();
    }
    
}
