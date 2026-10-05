# StudyPlanner

Projekt zaliczeniowy – Programowanie aplikacji internetowych MVC.

Autor: Illia Pryimak, nr albumu 164657.

Repozytorium: https://github.com/PryimakIllia/mvc-projekt-pryimak

## Temat

StudyPlanner to aplikacja internetowa wspierająca organizację nauki.
Docelowo student będzie zarządzać przedmiotami i zadaniami, określać terminy
oraz śledzić postęp wykonania. Projekt będzie rozwijany etapami w dwóch technologiach.

## Struktura repozytorium

- `czesc1-servlet-jsp` – aplikacja Servlet + JSP + JSTL, Tomcat 10.1.
- `czesc2-spring-boot` – przyszła aplikacja Spring Boot + Thymeleaf.
- `docs` – dokumentacja i tekst zgłoszenia tematu.

## Zakres etapu 1

- Projekt Maven z pakowaniem WAR i docelową wersją Java 21.
- Strona `index.html` z linkiem GET i formularzem POST.
- `StartServlet` z adnotacją `@WebServlet("/start")`.
- Obsługa UTF-8, walidacja imienia oraz wyświetlanie powitania.
- Wyświetlanie danych formularza jako tekstu dzięki kodowaniu znaków specjalnych HTML.

Etap 1 nie zapisuje danych i nie udostępnia jeszcze zarządzania zadaniami ani logowania.
JSP i JSTL będą użyte w dalszych etapach; zależności JSTL są już dodane.
Generowanie HTML w servlecie odpowiada zakresowi pierwszych zajęć.

## Wymagania

- JDK 21.x
- Maven dołączony do IntelliJ IDEA lub zewnętrzny Maven 3.9.x.
- Apache Tomcat 10.1; użyta wersja: 10.1.60.
- Git i konto GitHub.
- Dostęp do internetu przy pierwszym pobieraniu zależności Maven.

## Uruchomienie części I

### Budowanie w IntelliJ IDEA

1. Otwórz `czesc1-servlet-jsp/pom.xml` jako projekt Maven.
2. Ustaw JDK 21 dla projektu i Maven Runner.
3. Zsynchronizuj projekt Maven.
4. Uruchom `Lifecycle > clean`, następnie `Lifecycle > package`.
5. Sprawdź, czy powstał plik `czesc1-servlet-jsp/target/studyplanner.war`.

Alternatywnie, jeżeli zewnętrzny Maven jest dostępny w terminalu,
wykonaj w katalogu `czesc1-servlet-jsp`:

```bat
mvn clean package
```

### Konfiguracja i uruchomienie Tomcata

W sprawdzonym środowisku Windows Tomcat znajduje się w `C:\tomcat`.
Porty 8080 i 8081 były zajęte przez inne aplikacje, dlatego zastosowano port 8090.

Przed pierwszym uruchomieniem otwórz `C:\tomcat\conf\server.xml`
i ustaw `port="8090"` w aktywnym Connectorze HTTP/1.1.
Pozostałe ustawienia oraz przykładowe Connectory wewnątrz komentarzy XML pozostaw bez zmian.
Port 8090 musi być wolny na danym komputerze.

Skopiuj WAR i uruchom serwer w CMD:

```bat
copy /Y "C:\Projects\mvc-projekt-pryimak\czesc1-servlet-jsp\target\studyplanner.war" "C:\tomcat\webapps\studyplanner.war"
cd /d C:\tomcat\bin
catalina.bat run
```

Ścieżki należy dostosować, jeśli projekt lub Tomcat znajdują się w innych katalogach.
Tomcat musi korzystać z JDK 21, ponieważ WAR jest kompilowany dla tej wersji.
Okno CMD pozostaje otwarte podczas pracy aplikacji; Ctrl+C zatrzymuje serwer.

- Strona główna: http://localhost:8090/studyplanner/
- Servlet GET: http://localhost:8090/studyplanner/start
- Servlet POST: formularz na stronie głównej.

Na innym komputerze należy ponownie skonfigurować JDK, Maven i lokalny Tomcat.
Plik `server.xml` należy do instalacji Tomcata i nie jest częścią tego repozytorium.

## Sprawdzenie etapu

Aplikacja została uruchomiona na Tomcat 10.1.60, na porcie 8090.
Sprawdzono następujące scenariusze:

1. Wyświetlenie strony głównej z linkiem i formularzem.
2. Przejście przez link GET: odpowiedź zawiera `Metoda żądania: GET`.
3. Wysłanie imienia `Łukasz`: odpowiedź zawiera `Cześć, Łukasz!` i `POST`.
4. Poprawne wyświetlanie liter ukraińskich w imieniu.
5. Powrót na stronę główną.
6. Wysłanie samych spacji: komunikat walidacji zamiast powitania.
7. Wysłanie `<b>Jan</b>`: wyświetlenie dosłownego tekstu bez interpretacji jako HTML.

## Kolejne etapy

Planowane funkcje: lista przedmiotów, zadania z terminem, zmiana statusu,
edycja, usuwanie oraz filtrowanie. Szczegóły będą dostosowane do instrukcji kolejnych zajęć.
