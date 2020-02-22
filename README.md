# phoenix

## Background
phoenix erhebt SubTerranWars aus der Asche der gestorbenen Projekte. 

## Working Mode 
phoenix wird im "lean" Ansatz entwickelt um der Featuritis früherer Projekte begegnen zu können.

## Architecture


### Stack

#### Frontend
Browserbasiert , z. B. mittels 
- angular oder react mit boostrap
- oder unity

eher nicht in Frage kommen:
- Vaadin

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
 