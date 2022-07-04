create TRIGGER TR_TransportOffer
   ON  PaketZahtev
   AFTER Insert,Update
AS 
BEGIN
	
	declare @MyCursor CURSOR
	declare @eklidskaDistance decimal(10,3)
	declare @pocetnaCena decimal(10,3)
	declare @cenaPoKg decimal(10,3)
	declare @idPaketa int, @tezina int , @idAdreseOd int , @idAdreseDo int , @tipPaketa int


	SET @MyCursor = CURSOR FOR
	Select IdPaketa,Tezina,IdAdreseDo,IdAdreseDo,TipPaketa From inserted

	OPEN @MyCursor
	FETCH NEXT FROM @MyCursor INTO @idPaketa,@tezina,@idAdreseOd,@idAdreseDo,@tipPaketa
	WHILE @@FETCH_STATUS = 0
	BEGIN

	set @pocetnaCena = (select case @tipPaketa
						when 0 then 115
						when 1 then 175
						when 2 then 250
						when 3 then 350
						else 0
						end);

	set @cenaPoKg = (select case @tipPaketa
						when 0 then 0
						when 1 then 100
						when 2 then 100
						when 3 then 500
						else 0
						end);
	set @eklidskaDistance = (select sqrt(power(ap.X-ad.X,2) + power(ap.Y-ad.Y,2)) from PaketZahtev p join Adresa ap on p.IdAdreseOd = ap.IdAdrese join Adresa ad on p.IdAdreseDo = ad.IdAdrese where IdPaketa = @idPaketa)
	Update PaketZahtev set CenaIsporuke = (@pocetnaCena + @tezina * @cenaPoKg) * @eklidskaDistance where IdPaketa = @idPaketa

	FETCH NEXT FROM @MyCursor INTO @idPaketa,@tezina,@idAdreseOd,@idAdreseDo,@tipPaketa
	END
	CLOSE @MyCursor
	DEALLOCATE @MyCursor
	

END
GO