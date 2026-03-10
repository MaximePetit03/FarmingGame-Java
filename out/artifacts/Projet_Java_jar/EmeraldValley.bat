@echo off
setlocal
:: On se place dans le dossier du JAR
cd /d "%~dp0"

:: On définit le chemin vers ton dossier 'lib' (3 niveaux au-dessus)
set JFX_LIB="C:\Users\doudo\Documents\Java\Projet-Java\lib"

echo Lancement avec JavaFX...
:: Commande magique pour lier les modules
start javaw --module-path %JFX_LIB% --add-modules javafx.controls,javafx.fxml -jar "Projet-Java.jar"

exit