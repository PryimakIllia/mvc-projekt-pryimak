# StudyPlanner

Projekt zaliczeniowy – Programowanie aplikacji internetowych MVC.

Autor: [IMIĘ NAZWISKO], nr albumu [NUMER ALBUMU].
Repozytorium: https://github.com/[LOGIN]/mvc-projekt-[NAZWISKO]

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
- Obsługa UTF-8, sprawdzenie imienia i wyświetlenie powitania.

Etap 1 nie zapisuje danych, nie udostępnia jeszcze zarządzania zadaniami ani logowania.
JSP i JSTL będą użyte w dalszych etapach; zależności JSTL są już dodane.
Generowanie HTML w servlecie odpowiada zakresowi pierwszych zajęć.

## Wymagania

- JDK 21.
- Maven 3.9.x lub Maven dołączony do IntelliJ IDEA.
- Apache Tomcat 10.1.x.
- Git i konto GitHub.

## Uruchomienie części I

W katalogu `czesc1-servlet-jsp` wykonaj:

```bat
mvn clean package
```

Jeśli używasz Maven z IntelliJ: otwórz `pom.xml` jako projekt Maven,
ustaw JDK 21 dla projektu oraz Maven Runner i uruchom zadania
`Lifecycle > clean`, a następnie `Lifecycle > package`.

Po poprawnym zbudowaniu skopiuj `target/studyplanner.war` do katalogu
`webapps` serwera Tomcat 10.1. Na Windows uruchom w CMD:

```bat
cd /d C:\tomcat\bin
catalina.bat run
```

Przykładowa ścieżka zakłada, że katalog Tomcata został nazwany `C:\tomcat`.
Tomcat musi korzystać z JDK 21, ponieważ plik WAR jest kompilowany dla tej wersji.

- Strona główna: http://localhost:8080/studyplanner/
- Servlet GET: http://localhost:8080/studyplanner/start
- Servlet POST: formularz na stronie głównej.

W IntelliJ z obsługą Tomcata można użyć konfiguracji `Tomcat Server > Local`,
wdrożyć artefakt WAR exploded i ustawić kontekst `/studyplanner`.

## Sprawdzenie etapu

1. Otwórz stronę główną.
2. Kliknij link: oczekiwana odpowiedź zawiera `Metoda żądania: GET`.
3. Wyślij imię `Łukasz`: oczekiwana odpowiedź zawiera `Cześć, Łukasz!` i `POST`.
4. Sprawdź powrót na stronę główną.
5. Wyślij same spacje: oczekiwany komunikat walidacji i status HTTP 400.

## Kolejne etapy

Planowane funkcje: lista przedmiotów, zadania z terminem, zmiana statusu,
edycja, usuwanie oraz filtrowanie. Szczegóły będą dostosowane do instrukcji kolejnych zajęć.
