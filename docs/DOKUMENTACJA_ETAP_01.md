# StudyPlanner dokumentacja etapu 1

## Strona tytułowa

Nazwa aplikacji: StudyPlanner

Przedmiot: Programowanie aplikacji internetowych MVC

Autor: Illia Pryimak

Numer albumu: 164657

Repozytorium: https://github.com/PryimakIllia/mvc-projekt-pryimak

## 1.1 Cel aplikacji

Celem aplikacji StudyPlanner jest wsparcie studenta w organizowaniu nauki.
Aplikacja ma umożliwiać przechowywanie informacji o przedmiotach i powiązanych
z nimi zadaniach, kontrolowanie terminów oraz śledzenie postępu wykonania.
Projekt będzie rozwijany etapami zgodnie z zakresem kolejnych zajęć.

## 1.2 Typy użytkowników

Docelowym użytkownikiem jest student planujący swoje obowiązki związane z nauką.
Będzie mógł przeglądać przedmioty i zadania oraz zarządzać ich danymi.
Na etapie 1 dostępna jest publiczna strona startowa i formularz powitalny;
nie są jeszcze zaimplementowane konta użytkowników ani mechanizm logowania.
Ewentualne dodatkowe role zostaną określone zgodnie z wymaganiami kolejnych etapów.

## 1.3 Planowany zakres funkcji

- Dodawanie, przeglądanie, edycja i usuwanie przedmiotów.
- Dodawanie, przeglądanie, edycja i usuwanie zadań przypisanych do przedmiotu.
- Określanie terminu i statusu zadania.
- Zmiana statusu zadania, np. do wykonania, w trakcie, ukończone.
- Filtrowanie zadań według przedmiotu i statusu.

Powyższe funkcje stanowią plan rozwoju. Na etapie 1 zrealizowano stronę startową,
obsługę GET i POST, formularz powitalny, walidację imienia oraz obsługę UTF-8.
Sposób zapisu danych i szczegóły implementacji kolejnych funkcji zostaną
ustalone na podstawie instrukcji następnych zajęć.

## 3 Technologie

| Technologia | Wersja lub status | Zastosowanie |
|---|---|---|
| JDK | 21; pełny numer wersji 21.0.8 | Kompilacja i uruchamianie aplikacji |
| Maven | Maven dołączony do IntelliJ IDEA; numer wersji 10.1.60| Zarządzanie zależnościami i budowanie WAR |
| Apache Tomcat | 10.1.60 | Uruchomienie servletu |
| IntelliJ IDEA | Edycja i numer wersji 2025.2.3| Edytor i środowisko pracy |
| Git | Numer wersji 2.55.0.windows.5 | Historia zmian |
| Jakarta Servlet API | 6.0.0 | Obsługa HTTP; zależność provided |
| Jakarta JSTL API | 3.0.0 | Zależność przygotowana na kolejne etapy |
| Jakarta JSTL implementation | 3.0.1 | Zależność przygotowana na kolejne etapy |
| Maven Compiler Plugin | 3.13.0 | Kompilacja dla Java 21 |
| Maven WAR Plugin | 3.4.0 | Budowanie aplikacji webowej |
| Spring Boot i Thymeleaf | Planowane w części II; wersje jeszcze nieustalone | Druga implementacja aplikacji |

Część I jest przygotowywana w technologii Servlet + JSP + JSTL.
Na pierwszym etapie HTML jest generowany przez servlet zgodnie z przykładem
z pierwszych zajęć. Oddzielenie widoków i logiki zgodnie z MVC będzie rozwijane
w dalszych etapach.

## 8.1 Wymagania uruchomienia

Wymagane są JDK 21, Maven (zewnętrzny lub dołączony do IntelliJ) oraz Apache
Tomcat 10.1. Git jest potrzebny do pracy z repozytorium. Pierwsze budowanie
wymaga dostępu do internetu w celu pobrania zależności Maven.
Na etapie 1 nie jest wymagana baza danych.

Projekt został zbudowany w IntelliJ IDEA przez wykonanie zadań Maven
`Lifecycle > clean` oraz `Lifecycle > package`. Alternatywnie polecenie
`mvn clean package` można wykonać w katalogu `czesc1-servlet-jsp`,
jeżeli Maven jest dostępny w terminalu i korzysta z JDK 21.

W użytym środowisku Tomcat 10.1.60 znajduje się w katalogu `C:\tomcat`.
Plik `czesc1-servlet-jsp/target/studyplanner.war` został skopiowany do
`C:\tomcat\webapps`. Serwer jest uruchamiany w CMD przez `catalina.bat run`
z katalogu `C:\tomcat\bin`.

Tomcat został skonfigurowany na porcie HTTP 8090 przez zmianę wartości `port`
w aktywnym Connectorze HTTP/1.1 w pliku `C:\tomcat\conf\server.xml`.
Porty 8080 i 8081 były zajęte przez inne aplikacje. Port 8090 musi być wolny.
Na innym komputerze konfigurację lokalnej instalacji Tomcata trzeba wykonać osobno.

Adres strony głównej: http://localhost:8090/studyplanner/.

Adres servletu: http://localhost:8090/studyplanner/start.

Formularz na stronie głównej wysyła żądanie POST do servletu `/start`.
Podczas pracy aplikacji okno konsoli Tomcata pozostaje otwarte.

## 11 Dziennik etapów

| Data | Tag etapu | Co zrobiono | Problemy i rozwiązania |
|---|---|---|---|
| 2026-10-05 | etap-01 | Utworzono strukturę repozytorium i projekt Maven. Dodano StartServlet z obsługą GET i POST oraz stronę startową z formularzem. Zbudowano WAR w IntelliJ i uruchomiono aplikację na Tomcat 10.1.60. Sprawdzono GET, POST, UTF-8, walidację imienia i wyświetlanie znaków HTML jako tekstu. | Porty 8080 i 8081 były zajęte. Próby uruchomienia powodowały błąd Address already in use. Zmieniono port HTTP Tomcata na wolny port 8090; aplikacja działa pod adresem http://localhost:8090/studyplanner/. |
