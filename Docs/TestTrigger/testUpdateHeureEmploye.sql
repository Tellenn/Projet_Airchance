-- A tester
/*TODO : GET LES GENS, VOIR LEUR TEMPS DE VOL*/
select idEmploye, heuresVol
from PersonnelNaviguant natural join EmployeInstanceVol
where numInstance = 4;
/**GET LE VOL ET VOIR SA DUREE*/
select numVol, duree from Vol natural join InstanceVol where numInstance = 4;
/***MODFIFIER L'INSTANCE VOL SUR CE SCHEMA*/
update InstanceVol set etat = 'Arrive', dateArrivee = TO_DATE('2018/08/02 15:15:00', 'yyyy/mm/dd hh24:mi:ss') where numInstance = 4;
/****VERIFIER LES NOUVEAU TEMPS DE VOL*/
select idEmploye, heuresVol
from PersonnelNaviguant natural join EmployeInstanceVol
where numInstance = 4;