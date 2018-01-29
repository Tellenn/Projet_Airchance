
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
-- PN.heuresVol = SUM(PiloteModele.heuresModele) pour un même pilote
-- CREATE OR REPLACE TRIGGER t_7
-- AFTER INSERT OR UPDATE ON PersonnelNaviguant
-- FOR EACH ROW
-- DECLARE
-- 	totalHeures INTEGER;
-- BEGIN
-- 	SELECT SUM(heuresModele) INTO totalHeures
-- 	FROM PiloteModele
-- 	WHERE idEmploye = :new.idEmploye;

-- 	IF((:new.heuresVol > totalHeures) OR (:new.heuresVol < totalHeures)) THEN
-- 		RAISE_APPLICATION_ERROR(-20005, 'heuresVol de PN doit être égal à la somme des heuresModele de PiloteModele');
-- 	END IF;
-- END;
-- /
-- 