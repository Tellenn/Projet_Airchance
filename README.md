# Projet_Airchance

## Partie BD

### Lancer la BD
L'ensemble des fichiers sql nécessaire à la création, peuplement de la BD et des triggers se trouve dans le dossier BD contenant les fichiers suivants :

BD/InitBD : contient les scripts de création/peuplement de la BD et des triggers
BD/TestTrigger : contient les scripts de test pour chaque trigger
BD/init.sql : exécute l'ensemble des fichiers de création de la BD/triggers
BD/test.sql : exécute l'ensemble des ficgiers de test des triggers

Pour créer la BD et les triggers :
- Copier le contenu du dossier BD à la racine du serveur Oracle
- Taper la commande "start init" pour créer, peupler la base et créer les triggers
- Taper la commande "start test" pour tester les triggers