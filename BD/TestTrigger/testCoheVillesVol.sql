-- On va essayer de creer un vol avec la même ville de départ que d'arrivée (3 et 3)
INSERT INTO Vol VALUES (66, 0, 65, 422, 50, 30, 20, null, 3, 3);
-- Doit déclancher la trigger coheVillesVol