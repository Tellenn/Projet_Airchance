DROP TRIGGER t_1;
DROP TRIGGER t_2;
DROP TRIGGER t_3;
DROP TRIGGER t_4;
DROP TRIGGER t_5;
DROP TRIGGER t_6;

-- Si on affecte un PN à un vol qui part d'une ville où il ne se trouve pas
CREATE OR REPLACE TRIGGER t_1
AFTER INSERT OR UPDATE ON EmployeInstanceVol
FOR EACH ROW
DECLARE
	villeVol INTEGER;
	villePN INTEGER;
BEGIN
	SELECT idVilleOrigine INTO villeVol
	FROM Vol
	NATURAL JOIN InstanceVol
	WHERE numInstance = :new.numInstance;

	SELECT idDerniereVille INTO villePN
	FROM PersonnelNaviguant
	WHERE idEmploye = :new.idEmploye;

	IF(villeVol != villePN) THEN
		RAISE_APPLICATION_ERROR(-20001, 'PN pas dans la bonne ville pour repartir');
	END IF;
END;
/
-- Si on affecte un avion à un vol qui part d'une ville où il ne se trouve pas
CREATE OR REPLACE TRIGGER t_2
AFTER INSERT OR UPDATE ON InstanceVol
FOR EACH ROW
DECLARE
	villeAvion INTEGER;
	villeVol INTEGER;
BEGIN
	SELECT idDerniereVille INTO villeAvion
	FROM Avion
	WHERE idAvion = :new.idAvion;

	SELECT idVilleOrigine INTO villeVol
	FROM Vol
	WHERE numVol = :new.numVol;

	IF(villeAvion != villeVol) THEN
		RAISE_APPLICATION_ERROR(-20002, 'Avion pas dans la bonne ville pour repartir');
	END IF;
END;
/
-- Verifier qu'une instance de vol de propose pas plus de places que l'avion ne peut en contenir
CREATE OR REPLACE TRIGGER t_3
AFTER INSERT OR UPDATE ON InstanceVol
FOR EACH ROW
DECLARE
	nbPlacesEco INTEGER;
	nbPlacesAffaire INTEGER;
	nbPlacesPrem INTEGER;
BEGIN
	SELECT 	placesEco, placesAffaire, placesPrem
	INTO nbPlacesEco, nbPlacesAffaire, nbPlacesPrem
	FROM Avion
	WHERE idAvion = :new.idAvion;

	IF(:new.placesRestEco > nbPlacesEco OR :new.placesRestPrem > nbPlacesPrem OR :new.placesRestAff > nbPlacesAffaire) THEN
		RAISE_APPLICATION_ERROR(-20003, 'Une instance de vol ne peut pas proposer plus de places que l avion en dispose');
	END IF;
END;
/
-- Vérifier que l'avion dispose assez de place pour effectuer ce vol
CREATE OR REPLACE TRIGGER t_4
AFTER INSERT OR UPDATE ON InstanceVol
FOR EACH ROW
DECLARE
	placesEcoVol INTEGER;
	placesAffaireVol INTEGER;
	placesPremVol INTEGER;
	placesEcoAvion INTEGER;
	placesAffaireAvion INTEGER;
	placesPremAvion INTEGER;
BEGIN
	SELECT 	placesMinEco, placesMinAff, placesMinPrem 
	INTO placesEcoVol, placesAffaireVol, placesPremVol
	FROM Vol
	WHERE numVol = :new.numVol;

	SELECT 	placesEco, placesAffaire, placesPrem 
	INTO placesEcoAvion, placesAffaireAvion, placesPremAvion
	FROM Avion
	WHERE idAvion = :new.idAvion;

	IF(placesEcoAvion < placesEcoVol OR placesAffaireAvion < placesAffaireVol OR placesPremAvion < placesPremVol) THEN
		RAISE_APPLICATION_ERROR(-20004, 'L avion n a pas assez de place pour assurer ce vol');
	END IF;
END;
/
-- Maj place après réservation passager
CREATE OR REPLACE TRIGGER t_5
AFTER INSERT OR UPDATE ON ResaVolPlace
FOR EACH ROW
DECLARE
	classeResa VARCHAR2 (25);
BEGIN
	SELECT classe INTO classeResa
	FROM Place
	WHERE numPlace = :new.numPlace AND idAvion = :new.idAvion;

	IF(classeResa = 'Eco') THEN
		UPDATE InstanceVol SET placesRestEco = placesRestEco-1 WHERE numInstance = :new.numInstance;
	END IF;

	IF(classeResa = 'Affaire') THEN
		UPDATE InstanceVol SET placesRestAff = placesRestAff-1 WHERE numInstance = :new.numInstance;
	END IF;

	IF(classeResa = 'Premiere') THEN
		UPDATE InstanceVol SET placesRestPrem = placesRestPrem-1 WHERE numInstance = :new.numInstance;
	END IF;
END;
/
-- Maj poids après réservation fret
CREATE OR REPLACE TRIGGER t_6
AFTER INSERT OR UPDATE ON ReservationFret
FOR EACH ROW
BEGIN
	UPDATE InstanceVol SET poidsRest = poidsRest-:new.poids WHERE numInstance = :new.numInstance;
END;
/




-- CREATE OR REPLACE TRIGGER t_3
-- AFTER INSERT OR UPDATE ON ReservationFret
-- DECLARE
-- 	poids INTEGER;
-- FOR EACH ROW
-- BEGIN
-- 	SELECT SUM(poidsRest) INTO  poids
-- 	FROM InstanceVol
-- 	WHERE numInstance = :new.numInstance;

-- 	IF(poids < :new.poids) THEN
-- 		RAISE_APPLICATION_ERROR(-20003, 'Poids trop important');
-- 	END IF;
-- END;
