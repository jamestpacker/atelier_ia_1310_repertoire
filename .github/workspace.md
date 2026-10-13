
# Technologies imposées

Langage : Java 11 (LTS)

Interface graphique : Swing (pas de JavaFX, pas de framework web)

Base de données : SQLite via JDBC

Gestion de projet : Maven

Tests : JUnit 5 + Mockito


# Architecture imposée : découpage en couches

L'application doit
obligatoirement respecter le découpage en couches suivant, sans
dépendance croisée entre couches non adjacentes :

| Couche         | Rôle                                                  | Contrainte                                   |
| -------------- | ------------------------------------------------------ | -------------------------------------------- |
| Modèle        | Représentation des				données (Contact, Utilisateur) | Aucune dépendance à une				autre couche    |                                           |
| DAO            | Accès à la base de				données (CRUD)                | Aucune logique métier                       |
| Service        | Règles métier et				validations                      | Aucun import Swing                           |
| Contrôleur    | Orchestration Vue ↔				Service                        | Ne contient pas de logique				métier propre |
| Vue (UI Swing) | Affichage et interactions				utilisateur               | N'accède jamais				directement au DAO       |
