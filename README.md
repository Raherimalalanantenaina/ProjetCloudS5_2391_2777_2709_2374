Projet Cloud S5
Description du Projet
Le Projet Cloud S5 est une application déployée dans un environnement conteneurisé via Docker, offrant des fonctionnalités de fournisseur d'identite

Prérequis
Avant de commencer, assurez-vous que les outils suivants sont installés sur votre système :

Docker (version recommandée : 20.x ou supérieure).
Docker Compose (version recommandée : 2.x ou supérieure).
Un navigateur moderne (pour accéder à Swagger).
Installation
Clonez le dépôt du projet :

bash
Copier le code
git clone https://github.com/Raherimalalanantenaina/ProjetCloudS5_2391_2777_2709_2374.git
cd ProjetCouldS5-2391-2777-2709-2374
Vérifiez la configuration dans docker-compose.yml :

Assurez-vous que les ports nécessaires ne sont pas utilisés.nv).
Démarrez l'application avec Docker Compose :

bash
Copier le code
docker-compose up -d
Cela lancera tous les services requis en arrière-plan.

Accès à l'Application
Application principale :

Accédez à l'application via votre navigateur à l'adresse :
http://localhost:8080 (remplacez PORT par le port configuré dans docker-compose.yml, ici 8080).

Documentation Swagger :

La documentation Swagger est disponible à l'adresse suivante :
http://localhost:8080/swagger-ui.html (par défaut, 8080 si non modifié).

Structure du Projet
Dossier principal

-docker/postgres :contient le script de creation des table avec init.sql
-fournisseurIdentiteApi/ : Contient tout le code source de l'application.
-mcd/ : contient les fichiers lopping de mcd
-docker-compose.yml : Fichier de configuration Docker Compose pour déployer les services.
-README.md : Documentation détaillée du projet.

Services
Backend :
API REST développée avec Spring Boot.
Documentation API générée automatiquement via Swagger.
Base de données :
Base de données relationnelle PostgreSQL configurée dans le conteneur Docker.
Commandes Utiles
Démarrage et Arrêt des Conteneurs
Lancer les services :
bash
Copier le code
docker-compose up -d
Arrêter les services :
bash
Copier le code
docker-compose down
Vérification des Logs
Pour vérifier les logs des conteneurs en temps réel :

bash
Copier le code
docker-compose logs -f
Reconstruction des Conteneurs
Si vous apportez des modifications au code ou au fichier Dockerfile :

bash
Copier le code
docker-compose up -d --build
Détails Techniques
Ports Utilisés
Backend : Par défaut, le service backend écoute sur le port 8080. Assurez-vous que ce port est libre ou modifiez-le dans le fichier docker-compose.yml.
Configuration de la Base de Données
Service :PostgreSQL.
Nom d'utilisateur :
Mot de passe : 

Fonctionnalités Principales
API RESTful fournisseur d'identite.
Documentation Swagger pour tester et explorer les endpoints.
Environnement conteneurisé pour une portabilité maximale.
Support
Pour toute question ou problème, veuillez ouvrir une issue dans le dépôt Git ou contacter l'administrateur du projet.

Auteurs
Noms :  Ismael,Nantenaina,Rianala,Finaritra
ETU : 2391,2374,2709,2777
