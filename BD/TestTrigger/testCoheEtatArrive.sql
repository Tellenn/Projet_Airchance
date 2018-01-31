-- On essai d'indiquer qu'un avion est arrivé, sans préciser la date
update InstanceVol set etat = 'Arrive' where numInstance = 7;
-- Le ttrigger coheEtatArrive doit etre déclanché
