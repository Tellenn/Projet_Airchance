# Projet_Airchance

## Partie BD

L'ensemble des fichiers sql nécessaire à la création, peuplement de la BD et des triggers se trouve dans le dossier BD contenant les fichiers suivants :

- **BD/InitBD** : contient les scripts de création/peuplement de la BD et des triggers
- **BD/TestTrigger** : contient les scripts de test pour chaque trigger
- **BD/init.sql** : exécute l'ensemble des fichiers de création de la BD/triggers
- **BD/test.sql** : exécute l'ensemble des fichiers de test des triggers

Pour créer la BD et les triggers :
- Copier le contenu du dossier BD à la racine du serveur Oracle
- Taper la commande "start init" pour créer, peupler la base et créer les triggers
- Taper la commande "start test" pour tester les triggers

_Remarque : lorsqu'on crée la BD avec le script init.sql, les scripts sont lancés à la suite et le serveur peut garder dans son buffer la dernière commande sql du script précédent. On peut donc avoir des erreurs qui s'affichent mais qui n'en sont pas en réalité (il s'agit seulement de commande exécutée 2 fois pour une raison inconnue)._


## Partie Application
Les fichiers principaux sont GUI/PartieManager et GUI/PartieClient qui correspondent aux interfaces utilisateurs de la partie client et manager. La partie client est commencée mais non terminée. Les scénarios présentés dans le compte rendu n°2 sont implementés dans des fichiers JUnit dans Tests Packages/ les tests ont été effectués avec le debugger pour ralentir les transactions et observer la concurrence.

Tout le projet a été developpé sous Netbeans, il peut donc avoir des problèmes lors d'une utilisation sous un autre IDE.
