/*Update les heures employé par rapport au nouveau vol*/
create or replace trigger updateHeureEmploye
before update on InstanceVol
for each row
declare
	dureeVol integer;
	modele varchar2(50);
begin
	if(:new.etat = 'Arrive') then
		select duree into dureeVol
		from vol
		where numVol = :new.numVol;

		--Update les heures sur modèle des PNT associés
		select nomModele into modele
		from Avion
		where idAvion = :new.idAvion;

		update PiloteModele
		set heuresModele = heuresModele + dureeVol
		where idEmploye in (
		select idEmploye
		from EmployeInstanceVol natural join PersonnelNaviguant
		where numInstance = :new.numInstance and typePN = 'PNT') and nomModele = modele;

	--Update l'employe
		update PersonnelNaviguant
		set heuresVol = heuresVol + dureeVol
		where idEmploye in (
			select idEmploye
			from EmployeInstanceVol
			where numInstance = :new.numInstance);

		--Update les clients Sur les vol passager uniquement
		update Client
		set heuresCumulees = heuresCumulees +dureeVol
		where idClient in (
			select idClient
			from reservationPassager natural join ResaVolPlace
			where numInstance = :new.numInstance);
	end if;
end;
/
/*Mise a jour des position avion et PN*/
create or replace trigger updatePosition
before update on InstanceVol
for each row
declare
	idVilleArrive integer;
begin
	if(:new.etat = 'Arrive') then
		select idVilleDestination into idVilleArrive
		from Vol
		where numVol = :new.numVol;

		update PersonnelNaviguant
		set idDerniereVille = idVilleArrive
		where idEmploye in (
			select idEmploye
			from EmployeInstanceVol
			where numInstance = :new.numInstance);

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
