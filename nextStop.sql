declare @kurir varchar(100);
declare @IdVoznje int;
declare @IdKoraka int;
set @kurir = 'postarBG';
set @IdVoznje = (select top 1 IdVoznje from Voznja  where KorisnickoIme = @kurir and StatusVoznje = 1)
if @IdVoznje is null
begin
	select -3 as StatusCode
	return
end
set @IdKoraka = (select min(IdKoraka) from Korak where Zavrsen = 0 and IdVoznje = @IdVoznje)
if @IdKoraka is null
begin
	-- nema vise, posalji pakete u magacin
	declare @idMagacina int, @tipPotrosnje int, @cenaGoriva int;
	declare @registracioniBroj varchar(100);
	declare @profit decimal(10,3);
	declare @brojIsporucenihPaketa int;
	declare @potrosnja decimal(10,3);
	set @registracioniBroj = (select top 1 RegistracioniBroj from Voznja where IdVoznje = @IdVoznje)
	set @tipPotrosnje = (select top 1 TipPotrosnje from Vozilo where RegistracioniBroj = @registracioniBroj)
	set @idMagacina = (select top 1 a.IdAdrese from LokacijaMagacina lm join Adresa a on lm.IdAdrese = a.IdAdrese where a.IdGrada = (select top 1 ak.IdGrada from Kurir k join KorisnikKupac kk on k.KorisnickoIme = kk.KorisnickoIme join Adresa ak on kk.IdAdrese = ak.IdAdrese where kk.KorisnickoIme = @kurir))
	set @profit = (select sum(p.CenaIsporuke) from PaketZahtev p join Korak k on p.IdPaketa = k.IdPaketa where k.IdVoznje = @IdVoznje and Zavrsen = 1 and PreuzmiIliOstavi = 1 and IdeUMagacin = 0);
	set @brojIsporucenihPaketa = (select count(*) from PaketZahtev p join Korak k on p.IdPaketa = k.IdPaketa where k.IdVoznje = @IdVoznje and Zavrsen = 1 and PreuzmiIliOstavi = 1 and IdeUMagacin = 0)
	set @cenaGoriva = case @tipPotrosnje when 0 then 15 when 1 then 32 when 2 then 36 else 0 end;
	set @potrosnja = 0;

	declare @cursor cursor
	declare @trenutnaAdresa int, @sledecaAdresa int , @distance decimal(10,3);
	set @trenutnaAdresa = @idMagacina;
	set @distance = 0;
	set @cursor = cursor for select IdAdrese from Korak where IdVoznje = @IdVoznje and IdeUMagacin = 0 order by IdKoraka;

	open @cursor
	fetch next from @cursor into @sledecaAdresa
	while @@FETCH_STATUS = 0
	begin
		
		set @distance = @distance + (select top 1 sqrt(power(a1.X - a2.X, 2) + power(a1.Y - a2.Y, 2)) as Distance from Adresa a1, Adresa a2 where a1.IdAdrese = @trenutnaAdresa and a2.IdAdrese = @sledecaAdresa)

		set @trenutnaAdresa = @sledecaAdresa

		fetch next from @cursor into @sledecaAdresa
	end
	close @cursor
	deallocate @cursor

	-- dodati jos vracanje u magacin
	set @distance = @distance + (select top 1 sqrt(power(a1.X - a2.X, 2) + power(a1.Y - a2.Y, 2)) as Distance from Adresa a1, Adresa a2 where a1.IdAdrese = @trenutnaAdresa and a2.IdAdrese = @idMagacina)

	update PaketZahtev set IdAdreseTrenutneLokacija_Magacin_ = @idMagacina where IdPaketa in (select IdPaketa from Korak where IdVoznje = @IdVoznje and IdeUMagacin = 1)
	insert into VoziloUMagacinu(IdAdrese,RegistracioniBroj) values(@idMagacina,@registracioniBroj)

	set @potrosnja = @distance * @cenaGoriva * (select top 1 PotrosnjaPoKm from Vozilo where RegistracioniBroj = @registracioniBroj);
	update Voznja set StatusVoznje = 0 where IdVoznje = @IdVoznje
	update Kurir set [Status] = 0 , RegistracioniBrojVozi = NULL, OstvareniProfit = OstvareniProfit + @profit - @potrosnja, BrojIsporucenihPaketa = BrojIsporucenihPaketa + @brojIsporucenihPaketa where KorisnickoIme = @kurir;
	update Vozilo set KorisnickoImeVozacaKojiVozi = null where RegistracioniBroj = @registracioniBroj;


	select -1 as StatusCode
	return
end
declare @idPaketa int 
set @idPaketa = (select top 1 IdPaketa from Korak where IdKoraka = @IdKoraka )
if (select top 1 PreuzmiIliOstavi from Korak where IdKoraka = @IdKoraka) = 0
begin -- preuzima se paket
	insert into NalazeSeUVozilu(IdPaketa,IdVoznje) values(@idPaketa,@IdVoznje)
	update PaketZahtev set StatusIsporuke = 2,IdAdreseTrenutneLokacija_Magacin_ = null where IdPaketa = @idPaketa
	update Korak set Zavrsen = 1 where IdKoraka = @IdKoraka

	declare @IdKoraka2 int;

	while 1 > 0
	begin
		set @IdKoraka2 = (select min(IdKoraka) from Korak where Zavrsen = 0 and IdVoznje = @IdVoznje)
		if @IdKoraka2 is null break
		if (select top 1 PreuzmiIliOstavi from Korak where IdKoraka = @IdKoraka2) != 0 break
		if (select top 1 IdAdrese from Korak where IdKoraka = @IdKoraka2) != (select top 1 IdAdrese from Korak where IdKoraka = @IdKoraka) break

		set @IdKoraka = @IdKoraka2
		set @idPaketa = (select top 1 IdPaketa from Korak where IdKoraka = @IdKoraka )

		insert into NalazeSeUVozilu(IdPaketa,IdVoznje) values(@idPaketa,@IdVoznje)
		update PaketZahtev set StatusIsporuke = 2,IdAdreseTrenutneLokacija_Magacin_ = null where IdPaketa = @idPaketa
		update Korak set Zavrsen = 1 where IdKoraka = @IdKoraka
	end

	select -2 as StatusCode
	return
end

delete from NalazeSeUVozilu where IdPaketa = @idPaketa and IdVoznje = @IdVoznje
update PaketZahtev set StatusIsporuke = 3 where IdPaketa = @idPaketa
update Korak set Zavrsen = 1 where IdKoraka = @IdKoraka
select @idPaketa as StatusCode

-- kraj upita

select * from Korak 
select * from Voznja
select * from PaketZahtev


select * from NalazeSeUVozilu
select * from Korak
select * from NalazeSeUVozilu
select * from Kurir


select * from Voznja

update PaketZahtev set CenaIsporuke = 1000 where IdPaketa = 1
insert into PaketZahtev(CenaIsporuke,IdAdreseOd,IdAdreseDo,KorisnickoIme,StatusIsporuke,Tezina,TipPaketa,VremeKreiranjaZahteva) values(1300,1,3,'vlade', 3,1200,1,getdate())
insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(3,2,1,1,0,1)

select * from dbo.Voznja

select * from PaketZahtev
select * from LokacijaMagacina
insert into PaketZahtev(CenaIsporuke,IdAdreseOd,IdAdreseDo,KorisnickoIme,StatusIsporuke,Tezina,TipPaketa,VremeKreiranjaZahteva,IdAdreseTrenutneLokacija_Magacin_) values(1300,1,3,'vlade', 2,1200,1,getdate(),1)

declare @IdCity int;
set @IdCity = 1;
select IdPaketa from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese where StatusIsporuke = 1 and a.IdGrada = @IdCity 
union 
select IdPaketa from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese where StatusIsporuke = 2 and p.IdAdreseTrenutneLokacija_Magacin_ is not null and a.IdGrada = @IdCity 
union
select IdPaketa from PaketZahtev p join Adresa a on p.IdAdreseDo = a.IdAdrese where StatusIsporuke = 3 and a.IdGrada = @IdCity 


declare @IdPaketaa int;
declare @StatusPaketa int;
set @IdPaketaa = 5;
set @StatusPaketa = (select StatusIsporuke from PaketZahtev where IdPaketa = @IdPaketaa);
print @StatusPaketa;
select case
when @StatusPaketa = 1 then (select a.IdGrada from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese where p.IdPaketa = @IdPaketaa)
when @StatusPaketa = 2 and (select IdAdreseTrenutneLokacija_Magacin_ from PaketZahtev where IdPaketa = @IdPaketaa) is not null then (select a.IdGrada from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese where p.IdPaketa = @IdPaketaa)
when @StatusPaketa = 3 then (select a.IdGrada from PaketZahtev p join Adresa a on p.IdAdreseDo = a.IdAdrese where p.IdPaketa = @IdPaketaa)
else (select -1)
end as IdGrada

select * from PaketZahtev