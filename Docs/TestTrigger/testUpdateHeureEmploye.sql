-- On consulte actuellement, les heures des employé du vol choisi (4)
select idEmploye, heuresVol
from PersonnelNaviguant natural join EmployeInstanceVol
where numInstance = 4;

-- On verifie le vol et combien de temps il dure
select numVol, duree from Vol natural join InstanceVol where numInstance = 4;

-- On valide que le vol est terminé
update InstanceVol set etat = 'Arrive', dateArrivee = TO_DATE('2018/08/02 15:15:00', 'yyyy/mm/dd hh24:mi:ss') where numInstance = 4;

-- On verifie que le total est mis a jour
select idEmploye, heuresVol
from PersonnelNaviguant natural join EmployeInstanceVol
where numInstance = 4;

-- NB, cela est également valable pour les clients.