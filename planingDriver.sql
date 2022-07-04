declare @kurir varchar(100);
declare @status_kurira int;
set @kurir = 'postarBG';
set @status_kurira = (select top 1 [Status] from Kurir where KorisnickoIme = @kurir);
if (@status_kurira is NULL or @status_kurira != 0)
	begin
		select 'Kurir nije slobodan ili ne postoji' as Error
		return
	end
declare @idGrada int;
declare @idAdreseMagacina int;
set @idGrada = (select top 1 a.IdGrada from KorisnikKupac kk join Kurir k on kk.KorisnickoIme = k.KorisnickoIme join Adresa a on kk.IdAdrese = a.IdAdrese
where kk.KorisnickoIme = @kurir);
set @idAdreseMagacina = (select top 1 lm.IdAdrese as Postoji from LokacijaMagacina lm join Adresa a on lm.IdAdrese = a.IdAdrese where a.IdGrada = @idGrada)
if @idAdreseMagacina is null
	begin
	select 'Ne postoji skladiste u tom grada' as Error
	return
	end -- nadji slobodno vozilo
declare @registracioniBroj varchar(100);
set @registracioniBroj = ( select top 1 RegistracioniBroj from VoziloUMagacinu where IdAdrese = @idAdreseMagacina)
if @registracioniBroj is null
	begin
		select 'Ne postoji slobodno vozilo u skladistu' as Error
		return
	end
declare @IdVoznje int;
insert into Voznja(RegistracioniBroj,KorisnickoIme) values(@registracioniBroj,@kurir)
set @IdVoznje = SCOPE_IDENTITY()
update Kurir set [Status] = 1 , RegistracioniBrojVozi = @registracioniBroj where KorisnickoIme = @kurir;
update Vozilo set KorisnickoImeVozacaKojiVozi = @kurir where RegistracioniBroj = @registracioniBroj
delete VoziloUMagacinu where RegistracioniBroj = @registracioniBroj;
declare @zadnjaPosecenaAdresa int
declare @nosivost decimal(10,3)
declare @cursor Cursor
set @nosivost = (select top 1 Nosivost from Vozilo where RegistracioniBroj = @registracioniBroj)
set @zadnjaPosecenaAdresa = @idAdreseMagacina

-- preuzimanje paketa iz grada u kojem se nalazi
set @cursor = cursor for select IdPaketa,IdAdreseOd, 0 as Zavrsen,0 as PruzmiIliOstavi, 0 as IdeUMagacin, @IdVoznje as IdVoznje 
	from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese
	where [StatusIsporuke] = 1 and a.IdGrada = @idGrada
	order by VremeKreiranjaZahteva asc
declare @cursorIdPaketa int, @cursorIdAdrese int, @cursorZavrsen int , @cursorPreuzmi int , @cursorIdeUMagacin int, @cursorIdVoznje int
open @cursor
fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
while @@FETCH_STATUS = 0
begin
	declare @tezinaPaketa decimal(10,3)
	set @tezinaPaketa = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)
	if @nosivost - @tezinaPaketa >= 0
	begin
		insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)
		set @nosivost = @nosivost - @tezinaPaketa
		set @zadnjaPosecenaAdresa = @cursorIdAdrese
	end

	fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
end
close @cursor
-- dohvati sve iz magacina po istom principu
set @cursor = cursor for select IdPaketa,IdAdreseTrenutneLokacija_Magacin_, 0 as Zavrsen,0 as PruzmiIliOstavi, 0 as IdeUMagacin, @IdVoznje as IdVoznje 
	from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese
	where IdAdreseTrenutneLokacija_Magacin_ is not null and [StatusIsporuke] = 2 and a.IdGrada = @idGrada
	order by VremeKreiranjaZahteva asc
open @cursor
fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
while @@FETCH_STATUS = 0
begin
	declare @tezinaPaketa2 decimal(10,3)
	set @tezinaPaketa2 = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)
	if @nosivost - @tezinaPaketa2 >= 0
	begin
		insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)
		set @nosivost = @nosivost - @tezinaPaketa2
		set @zadnjaPosecenaAdresa = @cursorIdAdrese
	end

	fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
end
close @cursor

-- pravljenje koraka za ostavljanje paketa
insert into ObidjeniGradovi(IdGrada,IdVoznje) values(@idGrada,@IdVoznje)

declare @IdPaketa int, @IdAdrese int, @distance float, @idPaketaPrev int;
set @idPaketaPrev = -1;


while 1 > 0
begin

	select top 1 @IdPaketa = p.IdPaketa,@IdAdrese = p.IdAdreseDo , @distance =sqrt(power(a.X - aTrenutno.X, 2) + power(a.Y - aTrenutno.Y, 2))
	from Korak k join PaketZahtev p on k.IdPaketa = p.IdPaketa join Adresa a on p.IdAdreseDo = a.IdAdrese join Adresa aTrenutno on aTrenutno.IdAdrese = @zadnjaPosecenaAdresa 
	where IdVoznje = @IdVoznje and PreuzmiIliOstavi = 0 and IdeUMagacin = 0 and (select count(*) from Korak where IdPaketa = k.IdPaketa and PreuzmiIliOstavi = 1) = 0
	order by 3


	if @idPaketaPrev = @IdPaketa break;

	set @idPaketaPrev = @IdPaketa

	insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@IdPaketa,@IdAdrese,0,1,0,@IdVoznje)
	set @nosivost = @nosivost + (select top 1 Tezina from PaketZahtev where IdPaketa = @IdPaketa)
	set @zadnjaPosecenaAdresa = @IdAdrese

	set @idGrada = (select top 1 a.IdGrada from Adresa a join ObidjeniGradovi o on a.IdGrada = o.IdGrada where IdVoznje = @IdVoznje and a.IdAdrese = @zadnjaPosecenaAdresa)
	if @idGrada is null
	begin
		set @idGrada = (select top 1IdGrada from Adresa where IdAdrese = @zadnjaPosecenaAdresa)
		insert into ObidjeniGradovi(IdGrada,IdVoznje) values(@idGrada,@IdVoznje)

		set @cursor = cursor for select IdPaketa,IdAdreseOd, 0 as Zavrsen,0 as PruzmiIliOstavi, 1 as IdeUMagacin, @IdVoznje as IdVoznje 
		from PaketZahtev p join Adresa a on p.IdAdreseOd = a.IdAdrese
		where [StatusIsporuke] = 1 and a.IdGrada = @idGrada
		order by VremeKreiranjaZahteva asc

		open @cursor
		fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
		while @@FETCH_STATUS = 0
		begin
			set @tezinaPaketa = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)
			if @nosivost - @tezinaPaketa >= 0
			begin
				insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)
				set @nosivost = @nosivost - @tezinaPaketa
				set @zadnjaPosecenaAdresa = @cursorIdAdrese
			end

			fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
		end
		close @cursor

		set @cursor = cursor for select IdPaketa,IdAdreseTrenutneLokacija_Magacin_, 0 as Zavrsen,0 as PruzmiIliOstavi, 1 as IdeUMagacin, @IdVoznje as IdVoznje 
		from PaketZahtev p join Adresa a on p.IdAdreseTrenutneLokacija_Magacin_ = a.IdAdrese
		where IdAdreseTrenutneLokacija_Magacin_ is not null and [StatusIsporuke] = 2 and a.IdGrada = @idGrada
		order by VremeKreiranjaZahteva asc
	
		open @cursor
		fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
		while @@FETCH_STATUS = 0
		begin
			set @tezinaPaketa2 = ( select top 1 Tezina from PaketZahtev where IdPaketa = @cursorIdPaketa)
			if @nosivost - @tezinaPaketa2 >= 0
			begin
				insert into Korak(IdPaketa,IdAdrese,Zavrsen,PreuzmiIliOstavi,IdeUMagacin,IdVoznje) values(@cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje)
				set @nosivost = @nosivost - @tezinaPaketa2
				set @zadnjaPosecenaAdresa = @cursorIdAdrese
			end

			fetch next from @cursor into @cursorIdPaketa,@cursorIdAdrese,@cursorZavrsen,@cursorPreuzmi,@cursorIdeUMagacin,@cursorIdVoznje
		end
		close @cursor

	end

end
deallocate @cursor


if (select count(*) from Korak where IdVoznje = @IdVoznje) = 0
begin
	update Kurir set [Status] = 0 , RegistracioniBrojVozi = NULL where KorisnickoIme = @kurir;
	update Vozilo set KorisnickoImeVozacaKojiVozi = null where RegistracioniBroj = @registracioniBroj
	insert into VoziloUMagacinu(IdAdrese,RegistracioniBroj) values(@idAdreseMagacina,@registracioniBroj)
	select 'Nije bilo paketa' as Error
	return
end

select 'Uspeno izvrseno' as Done