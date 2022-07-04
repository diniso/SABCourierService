CREATE TABLE [Administrator]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL 
)
go

CREATE TABLE [Adresa]
( 
	[IdAdrese]           integer  IDENTITY  NOT NULL ,
	[Ulica]              varchar(100)  NOT NULL ,
	[Broj]               integer  NOT NULL 
	CONSTRAINT [PozitivanBroj_209229231]
		CHECK  ( Broj >= 0 ),
	[X]                  decimal(10,3)  NOT NULL ,
	[Y]                  decimal(10,3)  NOT NULL ,
	[IdGrada]            integer  NOT NULL 
)
go

CREATE TABLE [Grad]
( 
	[IdGrada]            integer  IDENTITY  NOT NULL ,
	[Naziv]              varchar(100)  NOT NULL ,
	[PostanskiBroj]      varchar(100)  NOT NULL 
)
go

CREATE TABLE [Korak]
( 
	[IdKoraka]           integer  IDENTITY  NOT NULL ,
	[IdPaketa]           integer  NOT NULL ,
	[IdAdrese]           integer  NOT NULL ,
	[Zavrsen]            smallint  NOT NULL 
	CONSTRAINT [SkupOdDveVrednosti_721024454]
		CHECK  ( [Zavrsen]=0 OR [Zavrsen]=1 ),
	[PreuzmiIliOstavi]   smallint  NOT NULL 
	CONSTRAINT [SkupOdDveVrednosti_1844315110]
		CHECK  ( [PreuzmiIliOstavi]=0 OR [PreuzmiIliOstavi]=1 ),
	[IdeUMagacin]        smallint  NOT NULL 
	CONSTRAINT [SkupOdDveVrednosti_1029459559]
		CHECK  ( [IdeUMagacin]=0 OR [IdeUMagacin]=1 ),
	[IdVoznje]           integer  NOT NULL 
)
go

CREATE TABLE [KorisnikKupac]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[Lozinka]            varchar(100)  NOT NULL ,
	[Ime]                varchar(100)  NOT NULL ,
	[Prezime]            varchar(100)  NOT NULL ,
	[IdAdrese]           integer  NOT NULL 
)
go

CREATE TABLE [Kurir]
( 
	[BrojVozackeDozvole] varchar(100)  NOT NULL ,
	[BrojIsporucenihPaketa] integer  NOT NULL 
	CONSTRAINT [VrednostNula_1244526882]
		 DEFAULT  0
	CONSTRAINT [PozitivanBroj_1775968449]
		CHECK  ( BrojIsporucenihPaketa >= 0 ),
	[OstvareniProfit]    numeric(10,3)  NOT NULL 
	CONSTRAINT [VrednostNula_625825636]
		 DEFAULT  0,
	[Status]             varchar(100)  NOT NULL 
	CONSTRAINT [VrednostNula_561413229]
		 DEFAULT  0
	CONSTRAINT [SkupOdDveVrednosti_635851729]
		CHECK  ( [Status]=0 OR [Status]=1 ),
	[RegistracioniBrojVozi] varchar(100)  NULL ,
	[KorisnickoIme]      varchar(100)  NOT NULL 
)
go

CREATE TABLE [LokacijaMagacina]
( 
	[IdAdrese]           integer  NOT NULL 
)
go

CREATE TABLE [NalazeSeUVozilu]
( 
	[IdPaketa]           integer  NOT NULL ,
	[IdVoznje]           integer  NOT NULL 
)
go

CREATE TABLE [ObidjeniGradovi]
( 
	[IdVoznje]           integer  NOT NULL ,
	[IdGrada]            integer  NOT NULL ,
	[IdO]                integer  IDENTITY  NOT NULL 
)
go

CREATE TABLE [PaketZahtev]
( 
	[IdPaketa]           integer  IDENTITY  NOT NULL ,
	[TipPaketa]          smallint  NOT NULL 
	CONSTRAINT [SkupOdCetiriVrednosti_302775635]
		CHECK  ( [TipPaketa]=0 OR [TipPaketa]=1 OR [TipPaketa]=2 OR [TipPaketa]=3 ),
	[Tezina]             decimal(10,3)  NOT NULL 
	CONSTRAINT [PozitivanBroj_940295624]
		CHECK  ( Tezina >= 0 ),
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[CenaIsporuke]       decimal(10,3)  NOT NULL 
	CONSTRAINT [VrednostNula_522276686]
		 DEFAULT  0
	CONSTRAINT [PozitivanBroj_1563511958]
		CHECK  ( CenaIsporuke >= 0 ),
	[StatusIsporuke]     smallint  NOT NULL 
	CONSTRAINT [VrednostNula_1375189039]
		 DEFAULT  0
	CONSTRAINT [SkupOdPetVrednosti_446967454]
		CHECK  ( [StatusIsporuke]=0 OR [StatusIsporuke]=1 OR [StatusIsporuke]=2 OR [StatusIsporuke]=3 OR [StatusIsporuke]=4 ),
	[VremeKreiranjaZahteva] DATETIME  NOT NULL ,
	[VremePrihvatanjaZahteva] DATETIME  NULL ,
	[IdAdreseOd]         integer  NOT NULL ,
	[IdAdreseDo]         integer  NOT NULL ,
	[IdAdreseTrenutneLokacija_Magacin_] integer  NULL 
)
go

CREATE TABLE [PostaniKurir]
( 
	[BrojVozackeDozvole] varchar(100)  NOT NULL ,
	[KorisnickoIme]      varchar(100)  NOT NULL 
)
go

CREATE TABLE [Vozilo]
( 
	[RegistracioniBroj]  varchar(100)  NOT NULL ,
	[TipPotrosnje]       smallint  NOT NULL 
	CONSTRAINT [SkupOdTriVrednosti_1145071949]
		CHECK  ( [TipPotrosnje]=0 OR [TipPotrosnje]=1 OR [TipPotrosnje]=2 ),
	[PotrosnjaPoKm]      decimal(10,3)  NOT NULL 
	CONSTRAINT [PozitivanBroj_812270394]
		CHECK  ( PotrosnjaPoKm >= 0 ),
	[Nosivost]           decimal(10,3)  NOT NULL 
	CONSTRAINT [PozitivanBroj_1678571228]
		CHECK  ( Nosivost >= 0 ),
	[KorisnickoImeVozacaKojiVozi] varchar(100)  NULL 
)
go

CREATE TABLE [VoziloUMagacinu]
( 
	[IdAdrese]           integer  NOT NULL ,
	[RegistracioniBroj]  varchar(100)  NOT NULL 
)
go

CREATE TABLE [Voznja]
( 
	[RegistracioniBroj]  varchar(100)  NOT NULL ,
	[IdVoznje]           integer  IDENTITY  NOT NULL ,
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[StatusVoznje]       smallint  NOT NULL 
	CONSTRAINT [VrednostJedan_203445307]
		 DEFAULT  1
	CONSTRAINT [SkupOdDveVrednosti_2128191170]
		CHECK  ( [StatusVoznje]=0 OR [StatusVoznje]=1 )
)
go

ALTER TABLE [Administrator]
	ADD CONSTRAINT [XPKAdministrator] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [Adresa]
	ADD CONSTRAINT [XPKAdresa] PRIMARY KEY  CLUSTERED ([IdAdrese] ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XPKGrad] PRIMARY KEY  CLUSTERED ([IdGrada] ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XAK1PostalCode] UNIQUE ([PostanskiBroj]  ASC)
go

ALTER TABLE [Korak]
	ADD CONSTRAINT [XPKKorak] PRIMARY KEY  CLUSTERED ([IdKoraka] ASC)
go

ALTER TABLE [KorisnikKupac]
	ADD CONSTRAINT [XPKKorisnikKupac] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [XPKKurir] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [XAK1KurirBrojVozacke] UNIQUE ([BrojVozackeDozvole]  ASC)
go

ALTER TABLE [LokacijaMagacina]
	ADD CONSTRAINT [XPKLokacijaMagacina] PRIMARY KEY  CLUSTERED ([IdAdrese] ASC)
go

ALTER TABLE [NalazeSeUVozilu]
	ADD CONSTRAINT [XPKNalazeSeUVozilu] PRIMARY KEY  CLUSTERED ([IdPaketa] ASC,[IdVoznje] ASC)
go

ALTER TABLE [ObidjeniGradovi]
	ADD CONSTRAINT [XPKObidjeniGradovi] PRIMARY KEY  CLUSTERED ([IdO] ASC)
go

ALTER TABLE [PaketZahtev]
	ADD CONSTRAINT [XPKPaketZahtev] PRIMARY KEY  CLUSTERED ([IdPaketa] ASC)
go

ALTER TABLE [PostaniKurir]
	ADD CONSTRAINT [XPKPostaniKurir] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [PostaniKurir]
	ADD CONSTRAINT [XAK1PostaniKurirBrojVozacke] UNIQUE ([BrojVozackeDozvole]  ASC)
go

ALTER TABLE [Vozilo]
	ADD CONSTRAINT [XPKVozilo] PRIMARY KEY  CLUSTERED ([RegistracioniBroj] ASC)
go

ALTER TABLE [VoziloUMagacinu]
	ADD CONSTRAINT [XPKVoziloUMagacinu] PRIMARY KEY  CLUSTERED ([RegistracioniBroj] ASC)
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [XPKVoznja] PRIMARY KEY  CLUSTERED ([IdVoznje] ASC)
go


ALTER TABLE [Administrator]
	ADD CONSTRAINT [R_15] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KorisnikKupac]([KorisnickoIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Adresa]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([IdGrada]) REFERENCES [Grad]([IdGrada])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Korak]
	ADD CONSTRAINT [R_41] FOREIGN KEY ([IdPaketa]) REFERENCES [PaketZahtev]([IdPaketa])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Korak]
	ADD CONSTRAINT [R_42] FOREIGN KEY ([IdAdrese]) REFERENCES [Adresa]([IdAdrese])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Korak]
	ADD CONSTRAINT [R_45] FOREIGN KEY ([IdVoznje]) REFERENCES [Voznja]([IdVoznje])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [KorisnikKupac]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([IdAdrese]) REFERENCES [Adresa]([IdAdrese])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_18] FOREIGN KEY ([RegistracioniBrojVozi]) REFERENCES [Vozilo]([RegistracioniBroj])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_31] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KorisnikKupac]([KorisnickoIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [LokacijaMagacina]
	ADD CONSTRAINT [R_12] FOREIGN KEY ([IdAdrese]) REFERENCES [Adresa]([IdAdrese])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [NalazeSeUVozilu]
	ADD CONSTRAINT [R_34] FOREIGN KEY ([IdPaketa]) REFERENCES [PaketZahtev]([IdPaketa])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [NalazeSeUVozilu]
	ADD CONSTRAINT [R_35] FOREIGN KEY ([IdVoznje]) REFERENCES [Voznja]([IdVoznje])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [ObidjeniGradovi]
	ADD CONSTRAINT [R_43] FOREIGN KEY ([IdVoznje]) REFERENCES [Voznja]([IdVoznje])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [ObidjeniGradovi]
	ADD CONSTRAINT [R_44] FOREIGN KEY ([IdGrada]) REFERENCES [Grad]([IdGrada])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [PaketZahtev]
	ADD CONSTRAINT [R_29] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KorisnikKupac]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PaketZahtev]
	ADD CONSTRAINT [R_37] FOREIGN KEY ([IdAdreseOd]) REFERENCES [Adresa]([IdAdrese])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PaketZahtev]
	ADD CONSTRAINT [R_39] FOREIGN KEY ([IdAdreseDo]) REFERENCES [Adresa]([IdAdrese])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PaketZahtev]
	ADD CONSTRAINT [R_40] FOREIGN KEY ([IdAdreseTrenutneLokacija_Magacin_]) REFERENCES [Adresa]([IdAdrese])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [PostaniKurir]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KorisnikKupac]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Vozilo]
	ADD CONSTRAINT [R_19] FOREIGN KEY ([KorisnickoImeVozacaKojiVozi]) REFERENCES [Kurir]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [VoziloUMagacinu]
	ADD CONSTRAINT [R_25] FOREIGN KEY ([IdAdrese]) REFERENCES [LokacijaMagacina]([IdAdrese])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [VoziloUMagacinu]
	ADD CONSTRAINT [R_26] FOREIGN KEY ([RegistracioniBroj]) REFERENCES [Vozilo]([RegistracioniBroj])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Voznja]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([KorisnickoIme]) REFERENCES [Kurir]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [R_17] FOREIGN KEY ([RegistracioniBroj]) REFERENCES [Vozilo]([RegistracioniBroj])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go