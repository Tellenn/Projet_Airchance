-- test coheVilleDepartAvion
-- Lorsqu'on affecte un avion a une instance de vol, le trigger coheVilleDepartAvion verifie que cet avion se
-- trouve bien dans la ville de depart du vol
-- Ici on cree une nouvelle instance de vol : veut affecter l'avion 25 qui se trouve dans la 
-- ville 5 (Londres) a un vol en partance de la ville 6 (Francfort) => impossible donc déclenchement du trigger
INSERT INTO InstanceVol VALUES (8, 8, 25, 0, 0, 8, null, null, null, 'Cree');