
/*On vérifie que la distance a effectue soit cohérente avec le rayon possible de l'avion*/
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger coheDistRayon
before insert or update on InstanceVol
for each row
declare
	rayon integer;
	dist integer;
begin
	select rayonAction into rayon
	from modele
	where nomModele = (
		select nomModele
		from Avion
		where idAvion = :new.idAvion);
		
	select distance into dist
	from vol
	where numVol = :new.numVol;

	if(dist > rayon) then
		raise_application_error(-20003,'La distance a parcourir est trop grande');
	end if;
end;
/

/* On compte les places reservée sur un InstanceVol pour eviter les doublons*/
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger coheReservPlace
after insert or update on resaVolPlace
declare
	nberror integer;
begin
	select count(mistake) into nberror
	from (
		select count(numPlace) as mistake
		from resaVolPlace
		group by numInstance, numPlace
		)
	where mistake > 1;
	
	if nberror > 0 then
		raise_application_error(-20004,'Deux meme places ne peuvent pas etre attribuee');
	end if;
end;
/


/* Verifier qu'une date ai été mise si l'état d'un vol est arrivé*/
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger coheEtatArrive
after update on instanceVol
declare
	nberror integer;
begin
	select count(numInstance) into nberror
	from InstanceVol
	where etat = 'Arrive' and dateArrivee is null;

	if(nberror > 0)then
		raise_application_error(-20011,'Un avion doit avoir une dateArrivee si il est arrive');
	end if;
end;
/

/* Vérifier pour un vol que la ville d'arrive doit être differente que la ville de départ*/
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger coheVillesVol
before insert or update on Vol
for each row
begin
	if(:new.idVilleOrigine = :new.idVilleDestination) then
		raise_application_error(-20006,'Ville depart = ville arrive impossible');
	end if;
end;
/



-- Si on affecte un avion à un vol qui part d'une ville où il ne se trouve pas
CREATE OR REPLACE TRIGGER coheVilleDepartAvion
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

	IF((villeAvion != villeVol) AND (:new.etat = 'Cree')) THEN
		RAISE_APPLICATION_ERROR(-20002, 'Avion pas dans la bonne ville pour repartir');
	END IF;
END;
/


-- Verifier qu'une instance de vol de propose pas plus de places que l'avion ne peut en contenir
CREATE OR REPLACE TRIGGER coheNbPlacesInstance
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
CREATE OR REPLACE TRIGGER coheNbPlacesAvion
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

-- PN.heuresVol = SUM(PiloteModele.heuresModele) pour un même pilote
CREATE OR REPLACE TRIGGER coheHeuresVolPN
AFTER INSERT OR UPDATE ON PersonnelNaviguant
FOR EACH ROW
DECLARE
	totalHeures INTEGER;
BEGIN
	SELECT SUM(heuresModele) INTO totalHeures
	FROM PiloteModele
	WHERE idEmploye = :new.idEmploye AND typePN = 'PNT';

	IF((:new.heuresVol > totalHeures) OR (:new.heuresVol < totalHeures)) THEN
		RAISE_APPLICATION_ERROR(-20005, 'heuresVol de PN doit etre egal à la somme des heuresModele de PiloteModele');
	END IF;
END;
/
