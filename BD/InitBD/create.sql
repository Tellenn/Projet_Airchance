 ---------------------------------------------------------------
 -- PROJET AIRCHANCE // BLONDEL, DEDIEU-MEILLE, PERRIN, TROUVIN 
 ---------------------------------------------------------------

------------------------------------------------------------
-- drop all tables
------------------------------------------------------------
DROP TABLE Client 				CASCADE CONSTRAINTS;
DROP TABLE ReservationFret 		CASCADE CONSTRAINTS;
DROP TABLE ReservationPassager 	CASCADE CONSTRAINTS;
DROP TABLE Vol 					CASCADE CONSTRAINTS;
DROP TABLE InstanceVol 			CASCADE CONSTRAINTS;
DROP TABLE Avion 				CASCADE CONSTRAINTS;
DROP TABLE Place 				CASCADE CONSTRAINTS;
DROP TABLE PersonnelNaviguant 	CASCADE CONSTRAINTS;
DROP TABLE Langue 				CASCADE CONSTRAINTS;
DROP TABLE Modele 				CASCADE CONSTRAINTS;
DROP TABLE Ville 				CASCADE CONSTRAINTS;
DROP TABLE ResaVolPlace 		CASCADE CONSTRAINTS;
DROP TABLE EmployeInstanceVol 	CASCADE CONSTRAINTS;
DROP TABLE LanguePNC 			CASCADE CONSTRAINTS;
DROP TABLE PiloteModele 		CASCADE CONSTRAINTS;

------------------------------------------------------------
-- Table: Client*
------------------------------------------------------------
CREATE TABLE Client(
	idClient        NUMBER(10,0)  NOT NULL  ,
	nomClient       VARCHAR2 (25)	,
	prenomClient    VARCHAR2 (25)	,
	numRueClient	VARCHAR2 (10)	,
	rueClient 		VARCHAR2 (50)	,
	cpClient 		NUMBER 	 (5)	,
	villeClient 	VARCHAR2 (50)	,
	heuresCumulees  NUMBER	 (10,0)	,
	numPasseport    VARCHAR2 (25)	,
	CONSTRAINT Client_Pk PRIMARY KEY (idClient),
	CONSTRAINT ck_Client_heuresCumulees CHECK (heuresCumulees >= 0)
);

------------------------------------------------------------
-- Table: ReservationFret
------------------------------------------------------------
CREATE TABLE ReservationFret(
	numReservationF 	NUMBER(10,0)  NOT NULL  ,
	idClient 			NUMBER(10,0) ,
	numInstance 		NUMBER(10,0) ,
	volume 				NUMBER(10,0) ,
	poids 				NUMBER(10,0) ,
	dateReservation 	DATE		 ,
	CONSTRAINT ReservationFret_Pk PRIMARY KEY (numReservationF)
);

------------------------------------------------------------
-- Table: ReservationPassager
------------------------------------------------------------
CREATE TABLE ReservationPassager(
	numReservationP  NUMBER(10,0)  NOT NULL  ,
	idClient         NUMBER(10,0) ,
	dateReservation  DATE   	  ,
	CONSTRAINT ReservationPassager_Pk PRIMARY KEY (numReservationP)
);

------------------------------------------------------------
-- Table: Vol*
------------------------------------------------------------
CREATE TABLE Vol(
	numVol         		NUMBER (10,0) NOT NULL ,
	type           		NUMBER (1) 	  ,
	duree          		NUMBER (10,0) ,
	distance       		NUMBER (10,0) ,
	placesMinEco   		NUMBER (10,0) ,
	placesMinAff   		NUMBER (10,0) ,
	placesMinPrem  		NUMBER (10,0) ,
	poidsMin       		NUMBER (10,0) ,
	idVilleOrigine 		NUMBER (10,0) ,
	idVilleDestination 	NUMBER (10,0) ,
	CONSTRAINT Vol_Pk PRIMARY KEY (numVol) ,
	CONSTRAINT CHK_BOOLEAN_type CHECK (type IN (0,1))
);

------------------------------------------------------------
-- Table: InstanceVol
------------------------------------------------------------
CREATE TABLE InstanceVol(
	numInstance     NUMBER (10,0)  NOT NULL ,
	numVol          NUMBER (10,0) ,
	idAvion         NUMBER (10,0) ,
	placesRestEco   NUMBER (10,0) ,
	placesRestAff   NUMBER (10,0) ,
	placesRestPrem  NUMBER (10,0) ,
	poidsRest       NUMBER (10,0) ,
	dateDepart      DATE   		  ,
	dateArrivee     DATE   		  ,
	etat            VARCHAR2 (25) ,
	CONSTRAINT InstanceVol_Pk PRIMARY KEY (numInstance)
);

------------------------------------------------------------
-- Table: Avion*
------------------------------------------------------------
CREATE TABLE Avion(
	idAvion 		NUMBER(10,0)  NOT NULL  ,
	nomModele 		VARCHAR2 (25) ,
	poidsDispo 		NUMBER(10,0)  ,
	volumeDispo 	NUMBER(10,0)  ,
	placesEco 		NUMBER(10,0)  ,
	placesAffaire  	NUMBER(10,0)  ,
	placesPrem     	NUMBER(10,0)  ,
	typeAvion		VARCHAR2(20)  ,
	idDerniereVille NUMBER(10,0)  ,
	CONSTRAINT Avion_Pk PRIMARY KEY (idAvion)
);

------------------------------------------------------------
-- Table: Place*
------------------------------------------------------------
CREATE TABLE Place(
	numPlace  NUMBER(10,0)  NOT NULL  ,
	idAvion   NUMBER(10,0) ,
	position  VARCHAR2 (25) ,
	classe    VARCHAR2 (25) ,
	CONSTRAINT Place_Pk PRIMARY KEY (numPlace, idAvion),
	CONSTRAINT ck_Place_numPlace CHECK (numPlace > 0 AND numPlace != 13)
);

------------------------------------------------------------
-- Table: PersonnelNaviguant*
------------------------------------------------------------
CREATE TABLE PersonnelNaviguant(
	idEmploye NUMBER(10,0)  NOT NULL  ,
	nomEmploye		VARCHAR2 (25) ,
	prenomEmploye	VARCHAR2 (25) ,
	numRueEmploye	VARCHAR2 (10) ,
	rueEmploye 		VARCHAR2 (50) ,
	cpEmploye 		VARCHAR2 (5)  ,
	villeEmploye 	VARCHAR2 (50) ,
	heuresVol 		NUMBER(10,0)  ,
	typePN			VARCHAR2 (10) ,
	idDerniereVille	NUMBER(10,0)  ,
	CONSTRAINT PNT_Pk PRIMARY KEY (idEmploye),
	CONSTRAINT ck_PN_heuresVol CHECK (heuresVol >= 0)
);

------------------------------------------------------------
-- Table: Langue*
------------------------------------------------------------
CREATE TABLE Langue(
	nomLangue  VARCHAR2 (25)  NOT NULL ,
	CONSTRAINT Langue_Pk PRIMARY KEY (nomLangue)
);

------------------------------------------------------------
-- Table: Modele*
------------------------------------------------------------
CREATE TABLE Modele(
	nomModele  	VARCHAR2 (25) NOT NULL ,
	nbPilotes  	NUMBER(10,0) ,
	rayonAction NUMBER(10,0) ,
	CONSTRAINT Modele_Pk PRIMARY KEY (nomModele),
	CONSTRAINT ck_Modele_nbPilotes CHECK (nbPilotes > 0),
	CONSTRAINT ck_Modele_rayonAction CHECK (rayonAction > 0)
);

------------------------------------------------------------
-- Table: Ville*
------------------------------------------------------------
CREATE TABLE Ville(
	idVille    NUMBER(10,0)  NOT NULL ,
	nomVille   VARCHAR2 (25) ,
	paysVille  VARCHAR2 (25) ,
	CONSTRAINT Ville_Pk PRIMARY KEY (idVille)
);

------------------------------------------------------------
-- Table: ResaVolPlace
------------------------------------------------------------
CREATE TABLE ResaVolPlace(
	numReservationP NUMBER(10,0) NOT NULL ,
	numInstance     NUMBER(10,0) NOT NULL ,
	numPlace        NUMBER(10,0) NOT NULL ,
	idAvion   		NUMBER(10,0) NOT NULL ,
	prix            FLOAT (24)   		  ,
	CONSTRAINT ResaVolPlace_Pk PRIMARY KEY (numReservationP,numInstance,numPlace,idAvion)
);

------------------------------------------------------------
-- Table: EmployeInstanceVol
------------------------------------------------------------
CREATE TABLE EmployeInstanceVol(
	numInstance  NUMBER(10,0)  NOT NULL ,
	idEmploye    NUMBER(10,0)  NOT NULL ,
	CONSTRAINT EmployeInstanceVol_Pk PRIMARY KEY (numInstance,idEmploye)
);

------------------------------------------------------------
-- Table: LanguePNC*
------------------------------------------------------------
CREATE TABLE LanguePNC(
	nomLangue  VARCHAR2 (25)  NOT NULL ,
	idEmploye  NUMBER(10,0)  NOT NULL ,
	CONSTRAINT LanguePNC_Pk PRIMARY KEY (nomLangue,idEmploye)
);

------------------------------------------------------------
-- Table: PiloteModele*
------------------------------------------------------------
CREATE TABLE PiloteModele(
	nomModele 			VARCHAR2 (25) NOT NULL ,
	idEmploye 			NUMBER(10,0)  NOT NULL ,
	heuresModele 		INTEGER ,
	CONSTRAINT PiloteModele_Pk PRIMARY KEY (nomModele,idEmploye)
);

ALTER TABLE ReservationFret ADD FOREIGN KEY (idClient) REFERENCES Client(idClient);
ALTER TABLE ReservationFret ADD FOREIGN KEY (numInstance) REFERENCES InstanceVol(numInstance);
ALTER TABLE ReservationPassager ADD FOREIGN KEY (idClient) REFERENCES Client(idClient);
ALTER TABLE Vol ADD FOREIGN KEY (idVilleOrigine) REFERENCES Ville(idVille);
ALTER TABLE Vol ADD FOREIGN KEY (idVilleDestination) REFERENCES Ville(idVille);
ALTER TABLE InstanceVol ADD FOREIGN KEY (numVol) REFERENCES Vol(numVol);
ALTER TABLE InstanceVol ADD FOREIGN KEY (idAvion) REFERENCES Avion(idAvion);
ALTER TABLE Avion ADD FOREIGN KEY (nomModele) REFERENCES Modele(nomModele);
ALTER TABLE Avion ADD FOREIGN KEY (idDerniereVille) REFERENCES Ville(idVille);
ALTER TABLE Place ADD FOREIGN KEY (idAvion) REFERENCES Avion(idAvion);
ALTER TABLE ResaVolPlace ADD FOREIGN KEY (numReservationP) REFERENCES ReservationPassager(numReservationP);
ALTER TABLE ResaVolPlace ADD FOREIGN KEY (numInstance) REFERENCES InstanceVol(numInstance);
ALTER TABLE ResaVolPlace ADD FOREIGN KEY (numPlace, idAvion) REFERENCES Place(numPlace, idAvion);
ALTER TABLE EmployeInstanceVol ADD FOREIGN KEY (numInstance) REFERENCES InstanceVol(numInstance);
ALTER TABLE EmployeInstanceVol ADD FOREIGN KEY (idEmploye) REFERENCES PersonnelNaviguant(idEmploye);
ALTER TABLE LanguePNC ADD FOREIGN KEY (nomLangue) REFERENCES Langue(nomLangue);
ALTER TABLE LanguePNC ADD FOREIGN KEY (idEmploye) REFERENCES PersonnelNaviguant(idEmploye);
ALTER TABLE PiloteModele ADD FOREIGN KEY (nomModele) REFERENCES Modele(nomModele);
ALTER TABLE PiloteModele ADD FOREIGN KEY (idEmploye) REFERENCES PersonnelNaviguant(idEmploye);
ALTER TABLE PersonnelNaviguant ADD FOREIGN KEY (idDerniereVille) REFERENCES Ville(idVille);
