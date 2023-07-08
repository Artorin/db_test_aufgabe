//Readme

dataInput Ordner enthält die Wagenstandsdaten aus dem ZIP-Archiv im src/main/resources/dataInput

Ablauf:
    defaultController empfängt Request, ruft defaultService auf
    Service ruft XmlParser auf
    XmlParser sucht entsprechende Züge und Waggons, sendet eine Section oder 404 - not found error zurück

In einigen xml-Dateien sind die Felder: <number> leer (die Nummer des Waggons) -> deswegen, falls keine entsprechende zur Zugnummer Waggon-Nummer gefunden wird, wird 404 als response zurückgesendet.

In einigen xml-Dateien sind die Züge nicht eindeutig identifizierbar, weil es mehrere Tracks (Gleise) gibt mit gleichen Zugnummern und Wagennummern. Daher nach den Parametern aus dem Endpoint (Ril100, ZugNr, WagenNr), kann man keine eindeutige Antwort (Section-Gleisabschnitte) zurücksenden.
(In der Beschreibung zu den Wagenstandssolldaten wurde nicht gesagt, wie die xml-Dateien ausgefüllt werden müssen.)

Ein Beispiel:
    // Request: http://localhost:8081/station/AA/train/9/waggon/264
    // Antwort: {"sections":["E"]}

Die Tests liegen im src/test/java/com.example.db_test Ordner.