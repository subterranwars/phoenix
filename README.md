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


## Game Elements
### Resource Management
Resource management is taken care of in the resource module of the bunker. The resource management is divided in two parts: "finding resource deposits" and "Extracting resources from deposits".
#### Literature research of resource games
Examples of games that include resource finding:
- Mass Effect 2: https://www.youtube.com/watch?v=G3AMdbkfDdM
- Excavate: http://www.crazygames.com/game/excavate
- Glean: http://www.crazygames.com/game/glean
Popular game elements for resource finding games:
- train user dexterity
	- cf. Gears of War: reload timing mechanics
	- cf. Final Fantasy X: Combo mode timing mechanics
- rewards
- upgrades of equipment (and or training skills) to improve gaming efficiency
- highscores
- use planning of strategic elements
- locate resources via triangulation
	- e.g. Excavate allows finding resources with a "Minesweeper-like" mechanic
	- Mass effect gives hints of "Signal strength" according to scanning position
#### Finding resources
##### Simple version
The user can click on "Scan for X", where X represents any of the available resources. If the scan is completed and successfull, a new resource deposit will be available for extracting resources.
##### Ideas for an advanced version
- Locate resources manually at the beginning
- Use "Scans" to get hints where resources might be positioned
- Triangulate resource positions and find deposits
- later in the game, deposits can be located automatically - which takes just more time compared to the user doing it herself
#### Extracting resources
A resource deposit has three characteristics:
- amount: defines how many resources can be extracted in total from this deposit 
- purity: defines how fast resources can be extracted - higher purity higher hourly yields
- depth: defines how much energy the extraction will cost - the deeper the more energy is required
##### Simple version
The user can click on a resource deposit and order mining drones to extract resources. The drones will extract a certain amount of resources based on the purity of the deposit and consume energy according to the depth of the deposit.
##### Ideas for an advances version
- Drones fetch resources
- Player can use her "dexterity" or use tactical skills to support mining drones, e.g.
	- to avoid enemys
	- to avoid stones / explosives / other materials in the payload
- once a mini-mission is complete, the user is rewarded with a small amount of extra resources
- extra resources do not scale - become irrelevant in the mid-game
