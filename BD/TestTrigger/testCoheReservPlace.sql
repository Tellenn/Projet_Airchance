--On souhaite attribuer deux fois la même place, donc on créer deux reservation( 66 et 67)
-- Et on va leur attribuer la place 2 de l'avion 21 tout les deux
INSERT INTO ReservationPassager VALUES (66, 16, TO_DATE('2017/12/08 10:08:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO ResaVolPlace VALUES (66, 4, 2, 21, 12000);
INSERT INTO ReservationPassager VALUES (67,16, TO_DATE('2017/12/08 10:08:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO ResaVolPlace VALUES (67, 4, 2, 21, 12000);
--Déclanchement du trigger coheReservPlace