-- test coheNbPlacesInstance
-- Lors de la creation d'une nouvelle instance de vol, le trigger coheNbPlacesInstance verifie que pour chaque
-- classe on ne propose pas plus de places que l'avion ne peut en contenir
-- Ici on cree une nouvelle instance avec l'avion 24 ou l'on propose 19 places en premiere classe
-- Or l'avion 24 ne peut acceuillir que 16 passagers en premiere classe => impossible donc déclenchement du trigger
INSERT INTO InstanceVol VALUES (8, 1, 24, 0, 0, 19, null, null, null, 'Cree');