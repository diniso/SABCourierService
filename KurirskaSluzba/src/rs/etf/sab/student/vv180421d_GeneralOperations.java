/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rs.etf.sab.operations.GeneralOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_GeneralOperations implements GeneralOperations{

    @Override
    public void eraseAll() {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return;
        
        String query= "delete from ObidjeniGradovi;\n" + 
"  delete from Korak;\n" +
"  delete from VoziloUMagacinu;\n" +
"  delete from LokacijaMagacina;\n" +
"  delete from Voznja;\n" +
"  update Vozilo set KorisnickoImeVozacaKojiVozi = NULL;\n" +
"  delete from Vozilo;\n" +
"  delete from Kurir;\n" +
"  delete from PostaniKurir;\n" +
"  delete from Administrator;\n" +
"  delete from KorisnikKupac;\n" +
"  delete from PaketZahtev;\n" +
"  delete from Adresa;\n" +
"  delete from Grad;\n" + 
"  delete from NalazeSeUVozilu";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.executeUpdate();
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_GeneralOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
}
