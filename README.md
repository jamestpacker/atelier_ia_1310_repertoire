# Répertoire téléphonique

Application Java Swing de gestion de contacts, avec base SQLite et architecture en couches.

## Prérequis

- Java 17+
- Maven 3+
- Linux/macOS/Windows avec un terminal compatible

## Installation

1. Vérifier que Java est installé :
   ```bash
   java -version
   ```

2. Vérifier que Maven est installé :
   ```bash
   mvn -version
   ```

3. Cloner le projet et se placer à la racine :
   ```bash
   cd repertoire-telephonique
   ```

4. Télécharger les dépendances :
   ```bash
   mvn dependency:go-offline
   ```

   ou via Makefile :
   ```bash
   make install
   ```

## Lancement de l'application

Depuis la racine du projet :

```bash
mvn exec:java -Dexec.mainClass=com.repertoire.app.RepertoireApp
```

Ou avec le Makefile :

```bash
make run
```

## Identification par défaut

L'application initialise un utilisateur par défaut :

- identifiant : `admin`
- mot de passe : `admin`

## Tests

Exécuter les tests Java :

```bash
mvn test
```

Ou avec le Makefile :

```bash
make test
```

## Nettoyage

Supprimer les artefacts Maven générés :

```bash
mvn clean
```

Ou :

```bash
make clean
```

## Structure du projet

```text
src/
  main/
    java/
      com/repertoire/
        app/
        dao/
        model/
        service/
        ui/
        util/
  test/
    java/
      com/repertoire/
```

## Fonctionnalités prévues

- Connexion
- Ajout de contact
- Recherche de contact
- Mise à jour de contact
- Suppression de contact

## Remarque

La base de données SQLite est créée automatiquement dans le dossier `data/` lors du lancement de l'application.
