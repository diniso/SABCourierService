/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rs.etf.sab.operations.DriveOperation;

/**
 *
 * @author vulov
 */
public class vv180421d_DriveOperation implements DriveOperation{
    
    public boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
    ResultSetMetaData rsmd = rs.getMetaData();
    int columns = rsmd.getColumnCount();
    for (int x = 1; x <= columns; x++) {
        if (columnName.equals(rsmd.getColumnName(x))) {
            return true;
        }
    }
    return false;
}

    @Override
    public boolean planingDrive(String courierUsername) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return false;
        
        String query="declare @kurir varchar(100);\n" +
"declare @status_kurira int;\n" +
"set @kurir = ?;\n" +
"set @status_kurira = (select top 1 [Status] from Kurir where KorisnickoIme = @kurir);\n" +
"if (@status_kurira is NULL or @status_kurira != 0)\n" +
"	begin\n" +
"		select 'Kurir nije slobodan ili ne postoji' as Error\n" +
"		return\n" +
"	end\n" +
"declare @idGrada int;\n" +
"declare @idAdreseMagacina int;\n" +
"set @idGrada = (select top 1 a.IdGrada from KorisnikKupac kk join Kurir k on kk.KorisnickoIme = k.KorisnickoIme join Adresa a on kk.IdAdrese = a.IdAdrese\n" +
"where kk.KorisnickoIme = @kurir);\n" +
"set @idAdreseMagacina = (select top 1 lm.IdAdrese as Postoji from LokacijaMagacina lm join Adresa a on lm.IdAdrese = a.IdAdrese where a.IdGrada = @idGrada)\n" +
"if @idAdreseMagacina is null\n" +
"	begin\n" +
"	select 'Ne postoji skladiste u tom grada' as Error\n" +
"	return\n" +
"	end -- nadji slobodno vozilo\n" +
"declare @registracioniBroj varchar(100);\n" +
"set @registracioniBroj = ( select top 1 RegistracioniBroj from VoziloUMagacinu where IdAdrese = @idAdreseMagacina)\n" +
"if @registracioniBroj is null\n" +
"	begin\n" +
"		select 'Ne postoji slobodno vozilo u skladistu' as Error\n" +
"		return\n" +
"	end\n" +
"declare @IdVoznje int;\n" +
"insert into Voznja(RegistracioniBroj,KorisnickoIme) values(@registracioniBroj,@kurir)\n" +
"set @IdVoznje = SCOPE_IDENTITY()\n" +
"update Kurir set [Status] = 1 , RegistracioniBrojVozi = @registracioniBroj where KorisnickoIme = @kurir;\n" +
"update Vozilo set KorisnickoImeVozacaKojiVozi = @kurir where RegistracioniBroj = @registracioniBroj\n" +
"delete VoziloUMagacinu where RegistracioniBroj = @registracioniBroj;\n" +
"declare @zadnjaPosecenaAdresa int\n" +
"declare @nosivost decimal(10,3)\n" +
"declare @cursor Cursor\n" +
"set @nosivost = (select top 1 Nosivost from Vozilo where RegistracioniBroj = @registracioniBroj)\n" +
"set @zadnjaPosecenaAdresa = @idAdreseMagacina\n" +
"\n" +
"-- preuzimanje paketa iz grada u kojem se nalazi\n" +
"set @cursor = cursor for select IdPaketa,IdAdreseOd, 0 as Zavrsen,0 as PruzmiIliOstavi, 0 as IdeUMagacin, @IdVoznje as IdVoznje \n" +
"	from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese\n" +
"	where [StatusIsporuke] = 1 and a.IdGrada = @idGrada\n" +
"	order by VremeKreiranjaZahteva asc\n" +
"declare @cursorIdPaketa int, @cursorIdAdrese int, @cursorZavrsen int , @cursorPreuzmi int , @cursorIdeUMagacin int, @cursorIdVoznje int\n" +
"open @cursor\n" +
"fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"while @@FETCH_STATUS = 0\n" +
"begin\n" +
"	declare @tezinaPaketa decimal(10,3)\n" +
"	set @tezinaPaketa = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)\n" +
"	if @nosivost - @tezinaPaketa >= 0\n" +
"	begin\n" +
"		insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)\n" +
"		set @nosivost = @nosivost - @tezinaPaketa\n" +
"		set @zadnjaPosecenaAdresa = @cursorIdAdrese\n" +
"	end\n" +
"\n" +
"	fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"end\n" +
"close @cursor\n" +
"-- dohvati sve iz magacina po istom principu\n" +
"set @cursor = cursor for select IdPaketa,IdAdreseTrenutneLokacija_Magacin_, 0 as Zavrsen,0 as PruzmiIliOstavi, 0 as IdeUMagacin, @IdVoznje as IdVoznje \n" +
"	from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese\n" +
"	where IdAdreseTrenutneLokacija_Magacin_ is not null and [StatusIsporuke] = 2 and a.IdGrada = @idGrada\n" +
"	order by VremeKreiranjaZahteva asc\n" +
"open @cursor\n" +
"fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"while @@FETCH_STATUS = 0\n" +
"begin\n" +
"	declare @tezinaPaketa2 decimal(10,3)\n" +
"	set @tezinaPaketa2 = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)\n" +
"	if @nosivost - @tezinaPaketa2 >= 0\n" +
"	begin\n" +
"		insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)\n" +
"		set @nosivost = @nosivost - @tezinaPaketa2\n" +
"		set @zadnjaPosecenaAdresa = @cursorIdAdrese\n" +
"	end\n" +
"\n" +
"	fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"end\n" +
"close @cursor\n" +
"\n" +
"-- pravljenje koraka za ostavljanje paketa\n" +
"insert into ObidjeniGradovi(IdGrada,IdVoznje) values(@idGrada,@IdVoznje)\n" +
"\n" +
"declare @IdPaketa int, @IdAdrese int, @distance float, @idPaketaPrev int;\n" +
"set @idPaketaPrev = -1;\n" +
"\n" +
"\n" +
"while 1 > 0\n" +
"begin\n" +
"\n" +
"	select top 1 @IdPaketa = p.IdPaketa,@IdAdrese = p.IdAdreseDo , @distance =sqrt(power(a.X - aTrenutno.X, 2) + power(a.Y - aTrenutno.Y, 2))\n" +
"	from Korak k join PaketZahtev p on k.IdPaketa = p.IdPaketa join Adresa a on p.IdAdreseDo = a.IdAdrese join Adresa aTrenutno on aTrenutno.IdAdrese = @zadnjaPosecenaAdresa \n" +
"	where IdVoznje = @IdVoznje and PreuzmiIliOstavi = 0 and IdeUMagacin = 0 and (select count(*) from Korak where IdPaketa = k.IdPaketa and PreuzmiIliOstavi = 1) = 0\n" +
"	order by 3\n" +
"\n" +
"\n" +
"	if @idPaketaPrev = @IdPaketa break;\n" +
"\n" +
"	set @idPaketaPrev = @IdPaketa\n" +
"\n" +
"	insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@IdPaketa,@IdAdrese,0,1,0,@IdVoznje)\n" +
"	set @nosivost = @nosivost + (select top 1 Tezina from PaketZahtev where IdPaketa = @IdPaketa)\n" +
"	set @zadnjaPosecenaAdresa = @IdAdrese\n" +
"\n" +
"	set @idGrada = (select top 1 a.IdGrada from Adresa a join ObidjeniGradovi o on a.IdGrada = o.IdGrada where IdVoznje = @IdVoznje and a.IdAdrese = @zadnjaPosecenaAdresa)\n" +
"	if @idGrada is null\n" +
"	begin\n" +
"		set @idGrada = (select top 1IdGrada from Adresa where IdAdrese = @zadnjaPosecenaAdresa)\n" +
"		insert into ObidjeniGradovi(IdGrada,IdVoznje) values(@idGrada,@IdVoznje)\n" +
"\n" +
"		set @cursor = cursor for select IdPaketa,IdAdreseOd, 0 as Zavrsen,0 as PruzmiIliOstavi, 1 as IdeUMagacin, @IdVoznje as IdVoznje \n" +
"		from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese\n" +
"		where [StatusIsporuke] = 1 and a.IdGrada = @idGrada\n" +
"		order by VremeKreiranjaZahteva asc\n" +
"\n" +
"		open @cursor\n" +
"		fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"		while @@FETCH_STATUS = 0\n" +
"		begin\n" +
"			set @tezinaPaketa = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)\n" +
"			if @nosivost - @tezinaPaketa >= 0\n" +
"			begin\n" +
"				insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)\n" +
"				set @nosivost = @nosivost - @tezinaPaketa\n" +
"				set @zadnjaPosecenaAdresa = @cursorIdAdrese\n" +
"			end\n" +
"\n" +
"			fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"		end\n" +
"		close @cursor\n" +
"\n" +
"		set @cursor = cursor for select IdPaketa,IdAdreseTrenutneLokacija_Magacin_, 0 as Zavrsen,0 as PruzmiIliOstavi, 1 as IdeUMagacin, @IdVoznje as IdVoznje \n" +
"		from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese\n" +
"		where IdAdreseTrenutneLokacija_Magacin_ is not null and [StatusIsporuke] = 2 and a.IdGrada = @idGrada\n" +
"		order by VremeKreiranjaZahteva asc\n" +
"	\n" +
"		open @cursor\n" +
"		fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"		while @@FETCH_STATUS = 0\n" +
"		begin\n" +
"			set @tezinaPaketa2 = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)\n" +
"			if @nosivost - @tezinaPaketa2 >= 0\n" +
"			begin\n" +
"				insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)\n" +
"				set @nosivost = @nosivost - @tezinaPaketa2\n" +
"				set @zadnjaPosecenaAdresa = @cursorIdAdrese\n" +
"			end\n" +
"\n" +
"			fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje\n" +
"		end\n" +
"		close @cursor\n" +
"\n" +
"	end\n" +
"\n" +
"end\n" +
"deallocate @cursor\n" +
"\n" +
"\n" +
"if (select count(*) from Korak where IdVoznje = @IdVoznje) = 0\n" +
"begin\n" +
"	update Kurir set [Status] = 0 , RegistracioniBrojVozi = NULL where KorisnickoIme = @kurir;\n" +
"	update Vozilo set KorisnickoImeVozacaKojiVozi = null where RegistracioniBroj = @registracioniBroj\n" +
"	insert into VoziloUMagacinu(IdAdrese,RegistracioniBroj) values(@idAdreseMagacina,@registracioniBroj)\n" +
"	select 'Nije bilo paketa' as Error\n" +
"	return\n" +
"end\n" +
"\n" +
"select 'Uspeno izvrseno' as Done";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, courierUsername);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && hasColumn(rs, "Error")) {
                System.out.println(rs.getString("Error"));
                rs.close();
                return false;
            }
            
            rs.close();
            return true;
            
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(vv180421d_DriveOperation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public int nextStop(String courierUsername) {
        Connection conn=DB.getInstance().getConnection();
        if (conn == null) return -3;
        
         String query="declare @kurir varchar(100);\n" +
"declare @IdVoznje int;\n" +
"declare @IdKoraka int;\n" +
"set @kurir = ?;\n" +
"set @IdVoznje = (select top 1 IdVoznje from Voznja  where KorisnickoIme = @kurir and StatusVoznje = 1)\n" +
"if @IdVoznje is null\n" +
"begin\n" +
"	select -3 as StatusCode\n" +
"	return\n" +
"end\n" +
"set @IdKoraka = (select min(IdKoraka) from Korak where Zavrsen = 0 and IdVoznje = @IdVoznje)\n" +
"if @IdKoraka is null\n" +
"begin\n" +
"	-- nema vise, posalji pakete u magacin\n" +
"	declare @idMagacina int, @tipPotrosnje int, @cenaGoriva int;\n" +
"	declare @registracioniBroj varchar(100);\n" +
"	declare @profit decimal(10,3);\n" +
"	declare @brojIsporucenihPaketa int;\n" +
"	declare @potrosnja decimal(10,3);\n" +
"	set @registracioniBroj = (select top 1 RegistracioniBroj from Voznja where IdVoznje = @IdVoznje)\n" +
"	set @tipPotrosnje = (select top 1 TipPotrosnje from Vozilo where RegistracioniBroj = @registracioniBroj)\n" +
"	set @idMagacina = (select top 1 a.IdAdrese from LokacijaMagacina lm join Adresa a on lm.IdAdrese = a.IdAdrese where a.IdGrada = (select top 1 ak.IdGrada from Kurir k join KorisnikKupac kk on k.KorisnickoIme = kk.KorisnickoIme join Adresa ak on kk.IdAdrese = ak.IdAdrese where kk.KorisnickoIme = @kurir))\n" +
"	set @profit = (select sum(p.CenaIsporuke) from PaketZahtev p join Korak k on p.IdPaketa = k.IdPaketa where k.IdVoznje = @IdVoznje and Zavrsen = 1 and PreuzmiIliOstavi = 1 and IdeUMagacin = 0);\n" +
"	set @brojIsporucenihPaketa = (select count(*) from PaketZahtev p join Korak k on p.IdPaketa = k.IdPaketa where k.IdVoznje = @IdVoznje and Zavrsen = 1 and PreuzmiIliOstavi = 1 and IdeUMagacin = 0)\n" +
"	set @cenaGoriva = case @tipPotrosnje when 0 then 15 when 1 then 32 when 2 then 36 else 0 end;\n" +
"	set @potrosnja = 0;\n" +
"\n" +
"	declare @cursor cursor\n" +
"	declare @trenutnaAdresa int, @sledecaAdresa int , @distance decimal(10,3);\n" +
"	set @trenutnaAdresa = @idMagacina;\n" +
"	set @distance = 0;\n" +
"	set @cursor = cursor for select IdAdrese from Korak where IdVoznje = @IdVoznje and IdeUMagacin = 0 order by IdKoraka;\n" +
"\n" +
"	open @cursor\n" +
"	fetch next from @cursor into @sledecaAdresa\n" +
"	while @@FETCH_STATUS = 0\n" +
"	begin\n" +
"		\n" +
"		set @distance = @distance + (select top 1 sqrt(power(a1.X - a2.X, 2) + power(a1.Y - a2.Y, 2)) as Distance from Adresa a1, Adresa a2 where a1.IdAdrese = @trenutnaAdresa and a2.IdAdrese = @sledecaAdresa)\n" +
"\n" +
"		set @trenutnaAdresa = @sledecaAdresa\n" +
"\n" +
"		fetch next from @cursor into @sledecaAdresa\n" +
"	end\n" +
"	close @cursor\n" +
"	deallocate @cursor\n" +
"\n" +
"	-- dodati jos vracanje u magacin\n" +
"	set @distance = @distance + (select top 1 sqrt(power(a1.X - a2.X, 2) + power(a1.Y - a2.Y, 2)) as Distance from Adresa a1, Adresa a2 where a1.IdAdrese = @trenutnaAdresa and a2.IdAdrese = @idMagacina)\n" +
"\n" +
"	update PaketZahtev set IdAdreseTrenutneLokacija_Magacin_ = @idMagacina where IdPaketa in (select IdPaketa from Korak where IdVoznje = @IdVoznje and IdeUMagacin = 1)\n" +
"	insert into VoziloUMagacinu(IdAdrese,RegistracioniBroj) values(@idMagacina,@registracioniBroj)\n" +
"\n" +
"	set @potrosnja = @distance * @cenaGoriva * (select top 1 PotrosnjaPoKm from Vozilo where RegistracioniBroj = @registracioniBroj);\n" +
"	update Voznja set StatusVoznje = 0 where IdVoznje = @IdVoznje\n" +
"	update Kurir set [Status] = 0 , RegistracioniBrojVozi = NULL, OstvareniProfit = OstvareniProfit + @profit - @potrosnja, BrojIsporucenihPaketa = BrojIsporucenihPaketa + @brojIsporucenihPaketa where KorisnickoIme = @kurir;\n" +
"	update Vozilo set KorisnickoImeVozacaKojiVozi = null where RegistracioniBroj = @registracioniBroj;\n" +
"\n" +
"\n" +
"	select -1 as StatusCode\n" +
"	return\n" +
"end\n" +
"declare @idPaketa int \n" +
"set @idPaketa = (select top 1 IdPaketa from Korak where IdKoraka = @IdKoraka )\n" +
"if (select top 1 PreuzmiIliOstavi from Korak where IdKoraka = @IdKoraka) = 0\n" +
"begin -- preuzima se paket\n" +
"	insert into NalazeSeUVozilu(IdPaketa,IdVoznje) values(@idPaketa,@IdVoznje)\n" +
"	update PaketZahtev set StatusIsporuke = 2,IdAdreseTrenutneLokacija_Magacin_ = null where IdPaketa = @idPaketa\n" +
"	update Korak set Zavrsen = 1 where IdKoraka = @IdKoraka\n" +
"\n" +
"	declare @IdKoraka2 int;\n" +
"\n" +
"	while 1 > 0\n" +
"	begin\n" +
"		set @IdKoraka2 = (select min(IdKoraka) from Korak where Zavrsen = 0 and IdVoznje = @IdVoznje)\n" +
"		if @IdKoraka2 is null break\n" +
"		if (select top 1 PreuzmiIliOstavi from Korak where IdKoraka = @IdKoraka2) != 0 break\n" +
"		if (select top 1 IdAdrese from Korak where IdKoraka = @IdKoraka2) != (select top 1 IdAdrese from Korak where IdKoraka = @IdKoraka) break\n" +
"\n" +
"		set @IdKoraka = @IdKoraka2\n" +
"		set @idPaketa = (select top 1 IdPaketa from Korak where IdKoraka = @IdKoraka )\n" +
"\n" +
"		insert into NalazeSeUVozilu(IdPaketa,IdVoznje) values(@idPaketa,@IdVoznje)\n" +
"		update PaketZahtev set StatusIsporuke = 2,IdAdreseTrenutneLokacija_Magacin_ = null where IdPaketa = @idPaketa\n" +
"		update Korak set Zavrsen = 1 where IdKoraka = @IdKoraka\n" +
"	end\n" +
"	\n" +
"\n" +
"	select -2 as StatusCode\n" +
"	return\n" +
"end\n" +
"\n" +
"delete from NalazeSeUVozilu where IdPaketa = @idPaketa and IdVoznje = @IdVoznje\n" +
"update PaketZahtev set StatusIsporuke = 3 where IdPaketa = @idPaketa\n" +
"update Korak set Zavrsen = 1 where IdKoraka = @IdKoraka\n" +
"select @idPaketa as StatusCode";
        try (PreparedStatement ps=conn.prepareStatement(query)){

            ps.setString(1, courierUsername);
            ResultSet rs = ps.executeQuery();
            int resultCode = -3;
            if (rs.next()) {
                resultCode = rs.getInt("StatusCode");
            }
            rs.close();
            
            return resultCode;
            
        } catch (SQLException ex) {
         //   java.util.logging.Logger.getLogger(vv180421d_PackageOperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return -3;
    }

    @Override
    public List<Integer> getPackagesInVehicle(String courierUsername) {
          Connection conn=DB.getInstance().getConnection();
        if (conn == null) return new ArrayList<>();
        
        String query="declare @IdVoznje int;\n" +
"set @IdVoznje = (select top 1 IdVoznje from Voznja  where KorisnickoIme = ? and StatusVoznje = 1);\n" +
"if @IdVoznje is not null\n" +
"begin\n" +
"	select IdPaketa from NalazeSeUVozilu\n" +
"	where IdVoznje = @IdVoznje\n" +
"end";
        try (PreparedStatement ps=conn.prepareStatement(query);){
  
            ps.setString(1, courierUsername);
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
    
}
