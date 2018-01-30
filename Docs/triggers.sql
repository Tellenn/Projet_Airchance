/*Update les heures employé par rapport au nouveau vol*/
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger updateHeureEmploye
before insert on EmployeInstanceVol
for each row
declare
	heurevol number;
	oldhours number;
	avion number;
	oldhoursavion number;
	typeE VARCHAR2(10);
begin
	select typePN into typeE
	from PersonnelNaviguant
	where idEmploye = :new.idEmploye;

	IF(typeE = 'PNT') THEN
		select duree into heurevol
		from vol natural join instanceVol
		where numInstance = :new.numInstance;
		
		select heuresVol into oldhours
		from PersonnelNaviguant
		where idEmploye = :new.idEmploye;
		
		update PersonnelNaviguant
		set heuresVol = heurevol+oldhours
		where idEmploye = :new.idEmploye;
		
		select idAvion into avion
		from InstanceVol
		where numInstance = :new.numInstance;
		
		select heuresModele into oldhoursavion
		from PiloteModele
		where nomModele = (
			select nomModele
			from Avion
			where idAvion = avion) and idEmploye=:new.idEmploye;
			
		update PiloteModele
		set heuresModele = oldhoursavion+heurevol
		where nomModele = (
			select nomModele
			from Avion
			where idAvion = avion);
	END IF;
end;
/
	
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
		raise_application_error(-20004,'Deux meme placesne peuvent pas');
	end if;
end;
/

/* Verifier qu'une date ai été mise si l'état d'un vol est arrivé*/
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger coheEtatArrive
before update on instanceVol
for each row
begin
	if(:new.etat = 'arrive' and :new.dateArrivee = null) then
		raise_application_error(-20005,'Si avion arrivé, entrez l heure d arrivée');
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

/*Mise a jour des position avion et PN*/
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger nouvellePosition
before update on InstanceVol
for each row
declare
	idVilleArrive integer;
begin
	if(:new.etat = 'arrive') then
		select idVilleDestination into idVilleArrive
		from Vol
		where numVol = :new.numVol;
		
		For employe in (
			select idEmploye
			from EmployeInstanceVol
			where numInstance = :new.numInstance)
		LOOP
			update PersonnelNaviguant
			set idDerniereVille = idVilleArrive
			where idEmploye = employe.idEmploye;
		END LOOP;
		
		update Avion
		set idDerniereVille = idVilleArrive
		where idAvion = :new.idAvion;
	END if;
end;
/

/* Rajouter le poid dispo si une reservation est annulée */
/* JEU DE TEST A FAIRE, COMPILE */
create or replace trigger updatePoidSuppr
before delete on ReservationFret
for each row
declare
	poid integer;
	nouvPoid integer;
begin
	select poidsRest into poid
	from InstanceVol
	where numInstance = :old.numInstance;
	
	nouvPoid:= poid + :old.poids;

	update instanceVol
	set poidsRest =  nouvPoid
	where numInstance = :old.numInstance;
end;
/

/** Partie de Quentin*/

-- Si on affecte un PN à un vol qui part d'une ville où il ne se trouve pas
CREATE OR REPLACE TRIGGER coheVilleDepartPN
AFTER INSERT ON EmployeInstanceVol
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
-- Maj place après réservation passager
CREATE OR REPLACE TRIGGER updateNbPlacesInstance
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
CREATE OR REPLACE TRIGGER updatePoidsInstance
AFTER INSERT OR UPDATE ON ReservationFret
FOR EACH ROW
BEGIN
	UPDATE InstanceVol SET poidsRest = poidsRest-:new.poids WHERE numInstance = :new.numInstance;
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
