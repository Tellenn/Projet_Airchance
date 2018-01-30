-- On consulte actuellement, la position de l'avion de numInstance 4
select idAvion, idDerniereVille
from Avion natural join InstanceVol
where numInstance = 4;

-- On regarde le vol, sa position de départ, d'arrive
select numVol,idVilleOrigine,idVilleDestination
from vol natural join InstanceVol
where numInstance = 4;

-- On valide que le vol est terminé
update InstanceVol set etat = 'Arrive', dateArrivee = TO_DATE('2018/08/02 15:15:00', 'yyyy/mm/dd hh24:mi:ss') where numInstance = 4;

-- On verifie que l'avion ai bien bougé'
select idAvion, idDerniereVille
from Avion natural join InstanceVol
where numInstance = 4;

-- NB, cela est également valable pour les clients.