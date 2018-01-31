--On désire supprimer une reservation fret
select numinstance, poidsRest from instanceVol where numinstance = 7;
--On regarde le poid associé a une reservation
select numReservationF, numInstance, poids from ReservationFret where numReservationF = 1;
--On supprime la première reservation au pif
delete from ReservationFret where numReservationF = 1;
--On vérifie a peu près que c'est bon
select numinstance, poidsRest from instanceVol where numinstance = 7;
