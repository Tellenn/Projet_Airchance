-- On creer un vol qui a une distance de 150000
INSERT INTO Vol VALUES (66, 0, 480, 150000, 0, 0, 0, null, 1, 9);
-- On essai d'y affecter un avion qui n'aura pas assez de range
INSERT INTO InstanceVol VALUES (66, 66, 25, 0, 0, 0, null, TO_DATE('2017/12/22 10:00:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2018/01/28 20:55:00', 'yyyy/mm/dd hh24:mi:ss'), 'Arrive');
-- La trigger COHEDISTRAYON doit crash