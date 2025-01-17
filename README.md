# ToDoApp - README
## Installationsanleitung
### Voraussetzungen:
Android Studio

JDK 11 oder höher

Ein Android-Gerät oder Emulator zum Testen der App

### Schritt-für-Schritt-Anleitung:
Nutze entweder die vorgefertigte APK im Repository oder klone das Projekt wie folgt:

git clone https://github.com/dein-repository/to-do-app.git](https://github.com/Insaain/9-Persistenz-und-Datenbank-Anbindung.git

App ausführen:

Verbinde ein Android-Gerät mit deinem Computer oder starte einen Emulator in Android Studio.
Klicke auf den "Run"-Button in Android Studio, um die App auf deinem Gerät oder Emulator zu installieren und auszuführen.

## Funktionsbeschreibung
Die ToDoApp ermöglicht es den Nutzern, Aufgaben zu erstellen, zu bearbeiten, abzuschließen und zu löschen. Die wichtigsten Funktionen sind:

### Erstellen von Aufgaben:

Der Benutzer kann eine neue Aufgabe mit Name, Beschreibung, Priorität und Deadline erstellen.

### Bearbeiten von Aufgaben:

Bestehende Aufgaben können aktualisiert werden.

### Aufgaben abschließen:

Benutzer können Aufgaben als erledigt markieren.

### Aufgaben löschen:

Benutzer können Aufgaben entfernen, die nicht mehr benötigt werden.

### Filterung der Aufgaben:

Die App zeigt die Aufgaben nach ihrem Status (offen oder erledigt) getrennt an.

## Verwendete Technologien
### Android (Jetpack Compose):

Für die Benutzeroberfläche und die Verwaltung des Layouts.

### SQLite:

Zur Speicherung der Aufgaben lokal auf dem Gerät.

### Kotlin:

Die Hauptprogrammiersprache für die App.

### Material Design 3:

Für ein modernes und benutzerfreundliches UI-Design.

## Bekannte Probleme

### Doppelte Aufgaben werden nicht erkannt:

Momentan gibt es keine Validierung, um sicherzustellen, dass Aufgaben nicht doppelt erstellt werden (z. B. gleiche Namen, gleiche Deadline).

### Keine Synchronisation:

Die App funktioniert nur lokal und bietet keine Möglichkeit zur Synchronisation über mehrere Geräte hinweg oder mit einem Cloud-Dienst.
