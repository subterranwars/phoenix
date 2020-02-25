# phoenix

[![Subterranwars](https://circleci.com/gh/subterranwars/phoenix.svg?style=shield)](https://circleci.com/gh/subterranwars/phoenix)

## Background
phoenix erhebt SubTerranWars aus der Asche der gestorbenen Projekte. 

## Working Mode 
phoenix wird im "lean" Ansatz entwickelt um der Featuritis früherer Projekte begegnen zu können.

## Architecture

### Stack

#### Frontend
Browserbasiert , z. B. mittels 
- angular oder react mit boostrap + gameigen ui-elementen

eher nicht in Frage kommen:
- Vaadin
- unity

#### Backend

##### Database (DB)
Postgres wäre geeignet, aber zunächst kann auch auf H2 oder SQLite zurückgegriffen werden; Einsatz von JPA gibt Flexibilität bei Datenbankwahl

##### Servers
- Trennung von Spielmechanikberechnungen und User-Interface in zwei getrennten Servern: Game-Server & UI-Server 
- Daten in DB in "Parameter" und "Zustände" trennen
- Um Kollisionen der Server an der DB zu vermeiden: gegenseitiges "Read-only": UI-Server liest nur Zustände, Game-Server liest nur Parameter
- Alternativ: UI-Server liest nur Zustände, und ordnet "Prozesse" an, Game-Server berechnet Prozesse und aktualisiert Zustände
- Optional parallelisierte Game- / UI-Server bei hohem aufkommen

### Game Loop
- keine "Nachberechnung" der Spielzustände mehr
- dauerhafte Server-loop die regelmäßig Ticks durchrechnet und nach jedem Tick den Spielzustand aktualisiert
- Tick-intervall in Echtzeit sollte nur wenige Sekunden betragen für ein gutes Spielgefühl
- keine Zustandsaktualisierung zwischen Ticks
- Trennung von Echtzeit (walltime) und in-game-time
 
## Features
- Multiplayer
- 


## Was macht am Spielen Spaß?
- Räsel lösen (Schach, Go, Sokoban)
- Strategien entwerfen / Pläne schmieden (Go, Squad)
- komplexe (Spiel-)Mechaniken verstehen (Go, Squad, Stellaris, Kerbal)
- mit anderen im Wettbewerb stehen (z. B. Schach, Fußball, Squad)
- Spielfähigkeiten freischalten "ab jetzt kann ich ... tun" (z. B. Erfahrungspunkte sammeln in jedem RPG / Techtrees a la Civ / ), Beispiele
	- den ganz großen Panzer bauen
	- Feuerbälle zaubern
- eigene Fähigkeiten verbessern (Schach, Klettern, Bogenschießen, Geschicklichkeitsspiele, Kerbal)
- Simulation beobachten (Factorio, Siedler, Kerbal)
- Geschichten verfolgen & Immersion (RPGs, z. B. )