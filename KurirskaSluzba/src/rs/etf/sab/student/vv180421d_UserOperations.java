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
import rs.etf.sab.operations.UserOperations;

/**
 *
 * @author vulov
 */
public class vv180421d_UserOperations implements UserOperations{
    
    private static String formatUnknowNumberOfParameters(String... names) {
        String ret = "";
        int i = 0 ;
        for (String name: names) {
            if (i++ == 0) ret += "(?";
            else ret += ",?";
        }
        
        ret += ")";
        
        return ret;
    }
    
    private static boolean checkPassword(String str) {
        if (str == null || str.length() < 8) return false;
        
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean specialCharacter = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            else {
                specialCharacter = true;
            }
                    
            if(numberFlag && capitalFlag && lowerCaseFlag && specialCharacter)
                return true;
        }
        return false;
    }

    @Override
    public boolean insertUser(String userName, String firstName, String lastName, String password, int idAddress) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        if (firstName == null || firstName.isEmpty() || !Character.isUpperCase(firstName.charAt(0)) ) return false;
        if (lastName == null  || lastName.isEmpty() || !Character.isUpperCase(lastName.charAt(0)) ) return false;
        if (!checkPassword(password)) return false;
        
        String query="insert into KorisnikKupac(KorisnickoIme,Lozinka,Ime,Prezime,IdAdrese) values(?,?,?,?,?)";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setInt(5 , idAddress);
            if (ps.executeUpdate() == 1) return true;

        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_UserOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean declareAdmin(String userName) {
          Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="insert into Administrator(KorisnickoIme) values(?)";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, userName);
            if (ps.executeUpdate() == 1) return true;

        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_UserOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public int getSentPackages(String... userNames) {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return 0;
        
        String query = "if (select count(*) from KorisnikKupac where KorisnickoIme in " + formatUnknowNumberOfParameters(userNames) + ") = 0\n" +
"begin\n" +
"	select -1 as BrojPosiljki\n" +
"end\n" +
"else \n" +
"begin\n" +
"select count(*) as BrojPosiljki\n" +
"from PaketZahtev\n" +
"where KorisnickoIme in " + formatUnknowNumberOfParameters(userNames) + "\n" +     
"end";
        
 
        try (PreparedStatement ps=conn.prepareStatement(query);){
            int i = 1;
            for (String name: userNames) {
               ps.setString(i, name);
               ps.setString(userNames.length + i++, name);
            }
            ResultSet rs = ps.executeQuery();
            int brojPosiljki = 0;
            if (rs.next()) brojPosiljki = rs.getInt("BrojPosiljki");
            
            rs.close();
            return brojPosiljki;
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_UserOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return 0;
    }

    @Override
    public int deleteUsers(String... userNames) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return 0;
        
        String query="delete from KorisnikKupac\n" +
                "where KorisnickoIme in ";
        
        int i = 0 ;
        for (String name: userNames) {
            if (i++ == 0) query += "(?";
            else query += ",?";
        }
        
        query += ")";
        
        try (PreparedStatement ps=conn.prepareStatement(query);){
            i = 1;
            for (String name: userNames) {
               ps.setString(i++, name);
            }
            return ps.executeUpdate();
            
        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_UserOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return 0;
    }

    @Override
    public List<String> getAllUsers() {
         Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="select KorisnickoIme from KorisnikKupac";
        try (PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){
                
            List<String> usernames = new ArrayList<>();
            while (rs.next())
                usernames.add(rs.getString("KorisnickoIme"));
            
            return usernames;

        } catch (SQLException ex) {
        //    java.util.logging.Logger.getLogger(vv180421d_UserOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }
    
}
