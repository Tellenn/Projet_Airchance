CREATE OR REPLACE TRIGGER t_1
AFTER INSERT OR UPDATE ON TABLE EmployeInstanceVol
DECLARE
	villeVol INTEGER;
	villePN INTEGER;
FOR EACH ROW
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

CREATE OR REPLACE TRIGGER t_2
AFTER INSERT OR UPDATE ON TABLE InstanceVol
DECLARE
	villeAvion INTEGER;
	villeVol INTEGER;
FOR EACH ROW
BEGIN
	SELECT idDerniereVille INTO villeAvion
	FROM Avion
	WHERE idAvion = :new.idAvion;

	SELECT idVilleOrigine INTO villeVol
	FROM Vol
	WHERE numVol = :new.numVol;

	IF(villeAvion != villeVol) THEN
		RAISE_APPLICATION_ERROR(-20001, 'Avion pas dans la bonne ville pour repartir');
	END IF;
END;
/