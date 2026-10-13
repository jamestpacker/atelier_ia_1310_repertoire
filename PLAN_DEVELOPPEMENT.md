# Plan de développement du répertoire téléphonique

## 1. Objectif global

Ce projet vise à développer une application Java Swing de gestion de contacts avec une base SQLite, selon une architecture en couches :

- Modèle / domaine
- DAO / persistance
- Services / logique métier
- UI Swing
- Tests / validation
- Documentation / revue

Le développement doit suivre l’ordre logique des dépendances :

Modèle -> DAO -> Service -> Tests -> UI/Swing -> Reviewer -> Documentation

---

## 2. Base du projet (à inclure avant F1)

### Objectif
Mettre en place les fondations techniques et structurales du projet avant de développer les fonctionnalités utilisateur.

### Livrables attendus
- Structure Maven du projet Java 17
- Configuration des dépendances nécessaires
- Base SQLite et connexion JDBC
- Classes de modèle de base : Utilisateur, Contact
- Tables SQL initiales
- Architecture de base des packages
- Écran d'accueil et écran de connexion de base

### Couches concernées
- agent-modele
- Expert DAO Java Backend
- Agent Service
- Agent UI/Swing
- Tests/QA Agent

### Validation
- projet Maven compilable
- connexion SQLite fonctionnelle
- création des tables réussie
- écrans de base affichés sans erreur
- tests de validation de base en place

---

## 3. Fonctionnalité F1 – Connexion

### Objectif
L'utilisateur saisit un identifiant et un mot de passe. En cas d’échec, un message d’erreur s’affiche. En cas de succès, l’écran principal s’ouvre.

### Couches concernées
- agent-modele : création de la classe Utilisateur
- Expert DAO Java Backend : accès aux utilisateurs en base
- Agent Service : vérification du login
- Agent UI/Swing : formulaire de connexion et navigation
- Tests/QA Agent : tests sur la logique de connexion

### Détails de mise en œuvre
- Créer le modèle `Utilisateur` avec : identifiant, mot de passe
- Créer un DAO d’authentification : recherche utilisateur par identifiant
- Créer un service `AuthService` :
  - vérifier si l'utilisateur existe
  - comparer le mot de passe
  - retourner un résultat de connexion
- Créer l’écran de connexion Swing :
  - champs identifiant
  - champ mot de passe
  - bouton connexion
  - message d’erreur en cas d’échec
- À la validation, ouvrir l’écran principal

### Validation
- identifiant + mot de passe corrects -> écran principal ouvert
- identifiant ou mot de passe incorrect -> message d’erreur affiché
- aucune ouverture de l’écran principal en cas d’échec

---

## 4. Fonctionnalité F2 – Ajout de contact

### Objectif
L'utilisateur saisit les informations d'un nouveau contact (nom, prénom, téléphone, email) et l'enregistre dans le répertoire.

### Couches concernées
- agent-modele : modèle Contact
- Expert DAO Java Backend : insertion en base
- Agent Service : validation des données
- Agent UI/Swing : formulaire et bouton d’enregistrement
- Tests/QA Agent : tests sur les validations et l’insertion

### Détails de mise en œuvre
- Créer la classe `Contact` avec : nom, prénom, téléphone, email
- Créer le DAO `ContactDAO` avec méthode `ajouter(Contact contact)`
- Créer le service `ContactService` pour valider les informations avant insertion
- Créer un formulaire Swing pour remplir les champs
- Enregistrer dans SQLite puis confirmer à l’utilisateur

### Validation
- contact valide -> insertion réussie
- champs vides ou invalides -> refus avec message explicite
- contact bien présent dans le répertoire après insertion

---

## 5. Fonctionnalité F3 – Recherche de contact

### Objectif
L'utilisateur saisit un critère (nom, prénom ou numéro) et la liste des contacts affichée est filtrée en conséquence.

### Couches concernées
- Expert DAO Java Backend : requêtes SQL filtrées
- Agent Service : logique de recherche et agrégation des résultats
- Agent UI/Swing : champ de recherche et liste dynamique
- Tests/QA Agent : tests de recherche par critère

### Détails de mise en œuvre
- DAO `ContactDAO` avec méthodes de recherche par :
  - nom
  - prénom
  - téléphone / numéro
- Service `ContactService` exposant la logique de filtrage
- Ajouter un champ de recherche dans l’interface Swing
- Mettre à jour la liste affichée selon le texte saisi

### Validation
- recherche par nom -> résultats corrects
- recherche par prénom -> résultats corrects
- recherche par numéro -> résultats corrects
- cas sans résultat -> liste vide et message adapté

---

## 6. Fonctionnalité F4 – Mise à jour de contact

### Objectif
L'utilisateur sélectionne un contact existant, modifie ses informations et enregistre les changements.

### Couches concernées
- Expert DAO Java Backend : mise à jour en base
- Agent Service : validation et mise à jour de l’objet
- Agent UI/Swing : sélection + formulaire de modification
- Tests/QA Agent : tests de mise à jour

### Détails de mise en œuvre
- Ajouter une méthode `modifier(Contact contact)` au DAO
- Mettre à jour le service avec validation des nouvelles données
- Dans l’interface, sélectionner un contact depuis la liste
- Pré-remplir le formulaire avec ses informations
- Enregistrer les changements dans SQLite

### Validation
- contact sélectionné -> formulaire prérempli
- données modifiées -> sauvegarde réussie
- confirmation de mise à jour visible
- données réellement modifiées en base

---

## 7. Fonctionnalité F5 – Suppression de contact

### Objectif
L'utilisateur sélectionne un contact et le supprime définitivement, après confirmation.

### Couches concernées
- Expert DAO Java Backend : suppression SQL par identifiant
- Agent Service : validation de la suppression
- Agent UI/Swing : confirmation et action de suppression
- Tests/QA Agent : tests de suppression et d’annulation

### Détails de mise en œuvre
- Créer une méthode DAO `supprimerParId(int id)`
- Dans l’UI, demander une confirmation avant suppression
- Lors de l’acceptation, supprimer le contact puis rafraîchir la liste
- Gérer correctement le cas où le contact n’existe plus

### Validation
- confirmation acceptée -> suppression effective
- confirmation refusée -> aucun changement
- contact supprimé -> plus visible dans la liste

---

## 8. Ordre de développement recommandé

1. Base du projet
2. F1 – Connexion
3. F2 – Ajout de contact
4. F3 – Recherche de contact
5. F4 – Mise à jour de contact
6. F5 – Suppression de contact
7. Tests globaux et validation fonctionnelle
8. Revue de code et documentation

---

## 9. Dépendances entre fonctionnalités

- F1 dépend de la base du projet
- F2 dépend des fondations du modèle et du DAO
- F3 dépend de la base de contact et de sa persistance
- F4 dépend de F2 et de la recherche
- F5 dépend de F2 et de la sélection de contact

---

## 10. Critères de validation finale

La solution sera considérée comme prête lorsque :
- les 5 fonctionnalités sont fonctionnelles
- le login fonctionne correctement
- les contacts peuvent être créés, recherchés, mis à jour et supprimés
- les validations sont présentes
- les tests ciblés passent
- l’interface Swing est stable et cohérente
- la documentation technique est à jour

---

## 11. Rôle des agents par couche

### Modèle
- agent-modele
- responsabilité : classes métier et structures de données

### DAO
- Expert DAO Java Backend
- responsabilité : persistance, JDBC, SQLite, requêtes SQL, transactions simples

### Service
- Agent Service
- responsabilité : règles métier, validation, orchestration entre DAO et UI

### Tests
- Tests/QA Agent
- responsabilité : tests unitaires, tests de régression, validation fonctionnelle

### UI/Swing
- Agent UI/Swing
- responsabilité : formulaires, interactions utilisateur, écrans Java Swing

### Reviewer / Documentation
- Documentation and Reviewer
- responsabilité : revue, qualité technique et documentation

---

## 12. Conclusion

Le projet est bien adapté à une approche par couches avec une priorité donnée aux fondations avant la fonctionnalité de connexion, puis aux opérations CRUD sur les contacts. Cette organisation permet de limiter les risques, de clarifier les responsabilités, et d’optimiser les étapes de validation avant la finalisation.
