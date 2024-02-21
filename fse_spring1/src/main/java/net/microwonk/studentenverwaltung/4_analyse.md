# Analyse der erweiterten Studentenverwaltung in Spring Boot

1. Ports-And-Adapters-Architektur:
   - Die Anwendung folgt dem hexagonalen Architekturmuster (auch als Ports-and-Adapters-Architektur bekannt). Dieses Muster ermöglicht eine lose Kopplung der Anwendungskomponenten und erleichtert die Integration mit der Umgebung durch Ports und Adapter.
2. REST-API-Funktionen:
   - Die Anwendung bietet RESTful-APIs für die Kommunikation zwischen webbasierten Apps.
   - Sie verwendet das HTTP-Protokoll und überträgt Daten im JSON-Format.
3. Thymeleaf-Frontend-Funktionen:
   - Thymeleaf ist ein moderner serverseitiger Java-Template-Engine für Web- und Standalone-Umgebungen.
   - Es ermöglicht die Erstellung von eleganten natürlichen Templates, die sowohl im Browser korrekt angezeigt als auch als statische Prototypen verwendet werden können.
4. REST-API Exception-Handling:
   - Die Anwendung verwendet @ControllerAdvice, um Exceptions global zu behandeln.
   - Dies ermöglicht ein einheitliches Exception-Handling im gesamten System.
5. 1:n-Beziehungen:
   - Die Beziehung zwischen Kurs, Buchung und Student wird abgebildet.
   - Die Strategie zur Speicherung der Objekte verwendet EAGER-Loading aus der Datenbank.
6. Methoden zum Einfügen von Buchungen:
   - Die Anwendung bietet Methoden zum Hinzufügen von Buchungen über die gesamte Architektur.
7. Validierung:
   - Die Studentenklasse enthält beispielhafte Validierungen.
   - Thymeleaf zeigt Fehlermeldungen in der Benutzeroberfläche bei Validierungsfehlern an.
8. Swagger-UI:
   - Die Anwendung aktiviert Swagger-UI für die Dokumentation der APIs.
9. Exception-Handling für Studentenlöschung:
   - Eine eigene Exception wird eingeführt, wenn ein Student gelöscht werden soll, der bereits in einem Kurs eingeschrieben ist (Referentielle Integrität der DB).
   - Das Exception-Handling ist sowohl für REST-Controller als auch für MVC-Web-Thymeleaf-Controller implementiert.
10. Beispiel-Daten:
    - Die Anwendung verwendet einen Application-Runner zur Bereitstellung von Beispiel-Daten.
       Die erweiterte Studentenverwaltung in Spring Boot ist gut strukturiert und erfüllt die genannten Anforderungen. Sie bietet eine solide Grundlage für die Verwaltung von Studentendaten und -buchungen.