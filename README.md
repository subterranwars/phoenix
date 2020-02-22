# phoenix

## Background
phoenix erhebt SubTerranWars aus der Asche der gestorbenen Projekte. 

## Working Mode 
phoenix wird im "lean" Ansatz entwickelt um der Featuritis fr�herer Projekte begegnen zu k�nnen.

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
Postgres w�re geeignet, aber zun�chst kann auch auf H2 oder SQLite zur�ckgegriffen werden; Einsatz von JPA gibt Flexibilit�t bei Datenbankwahl

##### Servers
- Trennung von Spielmechanikberechnungen und User-Interface in zwei getrennten Servern: Game-Server & UI-Server 
- Daten in DB in "Parameter" und "Zust�nde" trennen
- Um Kollisionen der Server an der DB zu vermeiden: gegenseitiges "Read-only": UI-Server liest nur Zust�nde, Game-Server liest nur Parameter
- Alternativ: UI-Server liest nur Zust�nde, und ordnet "Prozesse" an, Game-Server berechnet Prozesse und aktualisiert Zust�nde
- Optional parallelisierte Game- / UI-Server bei hohem aufkommen

### Game Loop
- keine "Nachberechnung" der Spielzust�nde mehr
- dauerhafte Server-loop die regelm��ig Ticks durchrechnet und nach jedem Tick den Spielzustand aktualisiert
- Tick-intervall in Echtzeit sollte nur wenige Sekunden betragen f�r ein gutes Spielgef�hl
- keine Zustandsaktualisierung zwischen Ticks
- Trennung von Echtzeit (walltime) und in-game-time
 