# Makefile pour le projet Répertoire Téléphonique

MAVEN := mvn
APP_MAIN := com.repertoire.app.RepertoireApp

.PHONY: help install test run clean

help:
	@echo "Commandes disponibles :"
	@echo "  make install   - télécharge les dépendances Maven"
	@echo "  make test      - exécute les tests"
	@echo "  make run       - lance l'application Swing"
	@echo "  make clean     - nettoie les fichiers générés par Maven"

install:
	$(MAVEN) dependency:go-offline

test:
	$(MAVEN) test

run:
	$(MAVEN) exec:java -Dexec.mainClass=$(APP_MAIN)

clean:
	$(MAVEN) clean
