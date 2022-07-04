/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rs.etf.sab.operations.PackageOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_PackageOperations implements PackageOperations{

    @Override
    public int insertPackage(int addressFrom, int addressTo, String userName, int packageType, BigDecimal weight) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -1;
        
        String query="insert into PaketZahtev(IdAdreseOd, IdAdreseDo, KorisnickoIme, TipPaketa, Tezina, VremeKreiranjaZahteva) values(?,?,?,?,?, getdate())";
        try (PreparedStatement ps=conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, addressFrom);
            ps.setInt(2, addressTo);
            ps.setString(3, userName);
            ps.setInt(4, packageType);
            ps.setFloat(5, weight.floatValue());
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next())
                return rs.getInt(1); 
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public boolean acceptAnOffer(int packageId) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query=" declare @idPaketa int\n" +
"  set @idPaketa = ?;\n" +
"  if (select top 1 StatusIsporuke from PaketZahtev where IdPaketa = @idPaketa) = 0\n" +
"  begin\n" +
"	update PaketZahtev set StatusIsporuke = 1 , VremePrihvatanjaZahteva = getdate() where IdPaketa = @idPaketa;\n" +
"  end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean rejectAnOffer(int packageId) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query=" declare @idPaketa int\n" +
"  set @idPaketa = ?;\n" +
"  if (select top 1 StatusIsporuke from PaketZahtev where IdPaketa = @idPaketa) = 0\n" +
"  begin\n" +
"	update PaketZahtev set StatusIsporuke = 4 where IdPaketa = @idPaketa\n" +
"  end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<Integer> getAllPackages() {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select IdPaketa from PaketZahtev";
        try (PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){

            List<Integer> ids = new ArrayList<>();
            while (rs.next())
                ids.add(rs.getInt("IdPaketa"));
            
            return ids;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int type) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select IdPaketa from PaketZahtev where TipPaketa = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, type);
            ResultSet rs = ps.executeQuery();
            List<Integer> ids = new ArrayList<>();
            while (rs.next())
                ids.add(rs.getInt("IdPaketa"));
            
            rs.close();
            return ids;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getAllUndeliveredPackages() {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select IdPaketa from PaketZahtev where StatusIsporuke = 1 or StatusIsporuke = 2";
        try (PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();){
  
            List<Integer> ids = new ArrayList<>();
            while (rs.next())
                ids.add(rs.getInt("IdPaketa"));
            
            return ids;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getAllUndeliveredPackagesFromCity(int cityId) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select IdPaketa \n" +
"  from PaketZahtev pk join Adresa a on pk.IdAdreseOd = a.IdAdrese\n" +
"  where (StatusIsporuke = 2 or StatusIsporuke = 1) and a.IdGrada = ?";
        try (PreparedStatement ps=conn.prepareStatement(query);){
  
            ps.setInt(1, cityId);
            ResultSet rs = ps.executeQuery();
            List<Integer> ids = new ArrayList<>();
            while (rs.next())
                ids.add(rs.getInt("IdPaketa"));
            
            rs.close();
            return ids;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getAllPackagesCurrentlyAtCity(int cityId) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="declare @IdCity int;\n" +
"set @IdCity = ?;\n" +
"select IdPaketa from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese where StatusIsporuke = 1 and a.IdGrada = @IdCity \n" +
"union \n" +
"select IdPaketa from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese where StatusIsporuke = 2 and p.IdAdreseTrenutneLokacija_Magacin_ is not null and a.IdGrada = @IdCity \n" +
"union\n" +
"select IdPaketa from PaketZahtev p join Adresa a on p.IdAdreseDo = a.IdAdrese where StatusIsporuke = 3 and a.IdGrada = @IdCity ";
        try (PreparedStatement ps=conn.prepareStatement(query);){
  
            ps.setInt(1, cityId);
            ResultSet rs = ps.executeQuery();
            List<Integer> ids = new ArrayList<>();
            while (rs.next())
                ids.add(rs.getInt("IdPaketa"));
            
            rs.close();
            return ids;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public boolean deletePackage(int packageId) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="  declare @idPaketa int\n" +
"  declare @statusPaketa int\n" +
"  set @idPaketa = ?;\n" +
"  select top 1 @statusPaketa = StatusIsporuke from PaketZahtev where IdPaketa = @idPaketa\n" +
"  if @statusPaketa = 0 or @statusPaketa = 4\n" +
"  begin\n" +
"	delete from PaketZahtev where IdPaketa = @idPaketa\n" +
"  end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean changeWeight(int packageId, BigDecimal newWeight) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @idPaketa int\n" +
"  declare @statusPaketa int\n" +
"  declare @weight decimal(10,3)\n" +
"  set @idPaketa = ?;\n" +
"  set @weight = ?;\n" +
"  select top 1 @statusPaketa = StatusIsporuke from PaketZahtev where IdPaketa = @idPaketa\n" +
"  if @statusPaketa = 0\n" +
"  begin\n" +
"	update PaketZahtev set Tezina = @weight where IdPaketa = @idPaketa\n" +
"  end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            ps.setDouble(2, newWeight.doubleValue());
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean changeType(int packageId, int newType) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @idPaketa int\n" +
"  declare @statusPaketa int\n" +
"  declare @type int\n" +
"  set @idPaketa = ?;\n" +
"  set @type = ?;\n" +
"  select top 1 @statusPaketa = StatusIsporuke from PaketZahtev where IdPaketa = @idPaketa\n" +
"  if @statusPaketa = 0\n" +
"  begin\n" +
"	update PaketZahtev set TipPaketa = @type where IdPaketa = @idPaketa\n" +
"  end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            ps.setInt(2, newType);
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public int getDeliveryStatus(int packageId) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -1;
        
        String query="  select top 1 StatusIsporuke\n" +
"  from PaketZahtev\n" +
"  where IdPaketa = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            int statusIsporuke = -1;
            if (rs.next()) {
                statusIsporuke = rs.getInt("StatusIsporuke");
            }
            rs.close();
            return statusIsporuke;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public BigDecimal getPriceOfDelivery(int packageId) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return BigDecimal.valueOf(-1);
        
        String query="select top 1 CenaIsporuke\n" +
"  from PaketZahtev\n" +
"  where IdPaketa = ?";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            double cenaIsporuke = -1;
            if (rs.next()) {
                cenaIsporuke = rs.getDouble("CenaIsporuke");
                if (cenaIsporuke == 0) cenaIsporuke = -1;
            }
            rs.close();
            return BigDecimal.valueOf(cenaIsporuke);
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return BigDecimal.valueOf(-1);
    }

    @Override
    public int getCurrentLocationOfPackage(int packageId) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -1;
        
        String query="declare @IdPaketaa int;\n" +
"declare @StatusPaketa int;\n" +
"set @IdPaketaa = ?;\n" +
"set @StatusPaketa = (select StatusIsporuke from PaketZahtev where IdPaketa = @IdPaketaa);\n" +
"print @StatusPaketa;\n" +
"select case\n" +
"when @StatusPaketa = 1 then (select a.IdGrada from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese where p.IdPaketa = @IdPaketaa)\n" +
"when @StatusPaketa = 2 and (select IdAdreseTrenutneLokacija_Magacin_ from PaketZahtev where IdPaketa = @IdPaketaa) is not null then (select a.IdGrada from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese where p.IdPaketa = @IdPaketaa)\n" +
"when @StatusPaketa = 3 then (select a.IdGrada from PaketZahtev p join Adresa a on p.IdAdreseDo = a.IdAdrese where p.IdPaketa = @IdPaketaa)\n" +
"else (select -1)\n" +
"end as IdGrada";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            int idGrada = -1;
            if (rs.next()) {
                idGrada = rs.getInt("IdGrada");
            }
            rs.close();
            return idGrada;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public Date getAcceptanceTime(int packageId) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return null;
        
        String query="declare @idPaketa int\n" +
"  declare @statusPaketa int\n" +
"  set @idPaketa = ?;\n" +
"  select top 1 @statusPaketa = StatusIsporuke from PaketZahtev where IdPaketa = @idPaketa\n" +
"  if @statusPaketa = 1 or @statusPaketa = 2 or @statusPaketa = 3\n" +
"  begin\n" +
"	select top 1 VremePrihvatanjaZahteva from PaketZahtev where IdPaketa = @idPaketa\n" +
"  end";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            
            Date datum = null;
            if (rs.next()) datum = rs.getDate("VremePrihvatanjaZahteva");

            rs.close();
            
            return datum;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
