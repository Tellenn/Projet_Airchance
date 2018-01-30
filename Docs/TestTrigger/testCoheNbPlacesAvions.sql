-- test coheNbPlacesAvion
-- Lorsqu'on cree une nouvelle instance de vol, le trigger coheNbPlacesAvion verifie que l'avion dispose
-- qu'on affecte dispose bien d'assez de places (pour chaque classe) pour le vol
-- Ici, on cree un nouveau vol qui requiert au moins 20 places en premiere classe
-- On essaye ensuite de creer une nouvelle instance de ce vol avec l'avion 24 => impossible donc déclenchement du trigger
-- Or l'avion 24 ne dispose que de 16 places en premiere classe 
INSERT INTO Vol VALUES (21, 0, 480, 5835, 0, 0, 20, null, 1, 9);
INSERT INTO InstanceVol VALUES (8, 21, 24, 0, 0, 8, null, null, null, 'Cree');