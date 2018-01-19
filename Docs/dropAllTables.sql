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