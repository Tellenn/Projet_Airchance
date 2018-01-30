-- test updateNbPlacesInstance
-- Lorsqu'on enregistre une nouvelle reservation pour un vol de type 'passagers', on fait une insertion dans la table ResaVolPlace
-- pour chaque place reservee. Pour chaque place reservee le trigger updateNbPlacesInstance met a jour la table InstanceVol en decrementant
-- le nombre de place restante pour la bonne classe
SELECT * FROM InstanceVol WHERE numInstance = 5;
INSERT INTO ReservationPassager VALUES (21, 13, TO_DATE('2018/01/30 10:14:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO ResaVolPlace VALUES (21, 5, 3, 23, 6000);
SELECT * FROM InstanceVol WHERE numInstance = 5;