-- test coheHeuresVolPN
-- Le trigger coheHeuresVolPN verifie que l'attribut heuresVol de la table PersonnelNaviguant est bien egal a la somme des heures de vol que 
-- chaque pilote a passe sur les modeles qu'il a pilote
UPDATE PersonnelNaviguant SET heuresVol=0 WHERE idEmploye=1;