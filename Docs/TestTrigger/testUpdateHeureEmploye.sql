-- A tester
/*TODO : GET LES GENS, VOIR LEUR TEMPS DE VOL*/
select *
from PersonnelNaviguant natural join EmployeInstanceVol
where numInstanceVol = 7
/**GET LE VOL ET VOIR SA DUREE*/
select * from Vol natural join InstanceVol where numInstanceVol = 7;
/***MODFIFIER L'INSTANCE VOL SUR CE SCHEMA*/
update InstanceVol set etat = 'Arrive' and dateArrive = now() where numInstanceVol = 7;
/****VERIFIER LES NOUVEAU TEMPS DE VOL*/
select *
from PersonnelNaviguant natural join EmployeInstanceVol
where numInstanceVol = 7