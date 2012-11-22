Web Engineering - ESP App
====================================

Dieses Repository beinhaltet alle Projekte der ESP App.

Voraussetzungen: 
----------------
* JDK 1.6
* Eclipse Indigo SR2
* Checkstyle Plugin
* JBoss Tools Plugin
* EGit Plugin
* JBoss Application Server 6.1
* MySQL 5.5

Build Properties:
-----------------	
Für die ant Skripten existieren global-build.properties im Projekt "EspAppBuildEnv". Diese müssen individuell auf jedem Rechner, je nach Konfiguration angepasst werden. Speziell der Installationspfad des JBoss Application Server muss richtig gesetzt werden, da sonst der Build-Prozess fehlschlägt.

Es besteht die Möglichkeit die eingecheckten global-build.properties im Projekt "EspAppBuildEnv" zu überschreiben. Hierzu legt man eigene global-build.properties ins Verzeichnis <USER_HOME>/.ant/global-build.properties. Diese werden als erstes beim Build angezogen.

Build:
------
Die ESP App lässt sich per ant bauen. Das zugehörige Skript liegt im Projekt "EspApp".

    ant -buildfile EspApp/build.xml complete-build

Deployment:
-----------
Für das automatische Deployment ist ebenfalls ein ant Skript vorhanden.

    ant -buildfile EspApp/dev-build.xml deploy
