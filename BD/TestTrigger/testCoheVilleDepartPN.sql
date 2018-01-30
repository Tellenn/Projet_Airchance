-- test coheVilleDepartPN
-- Lorsqu'on affecte un employe a une instance de vol, le trigger coheVilleDepartPN verifie que cet employe
-- se trouve bien dans la ville de depart du vol
-- Ici on veut affecter l'employe 7 qui se trouve dans la ville 1 (Paris) a l'instance de
-- vol 4. Or ce vol part de la ville 5 (Londres) => impossible donc déclenchement du trigger
INSERT INTO EmployeInstanceVol VALUES (4, 7);