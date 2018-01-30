-- test updatePoidsInstance
-- Lorsqu'on enregistre une nouvelle reservation pour un vol de type 'fret', on fait une insertion dans la table ReservationFret
-- Pour chaque reservation le trigger updatePoidsInstance met a jour la table InstanceVol en decrementant le poids restant du poids qui a ete reserve
SELECT * FROM InstanceVol WHERE numInstance = 7;
INSERT INTO ReservationFret VALUES (2, 16, 7, 500, 5000, TO_DATE('2018/01/29 10:32:00', 'yyyy/mm/dd hh24:mi:ss'));
SELECT * FROM InstanceVol WHERE numInstance = 7;