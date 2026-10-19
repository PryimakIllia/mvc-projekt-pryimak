# StudyPlanner dokumentacja etapu 2

Autor: Illia Pryimak, numer albumu 164657.

Repozytorium: https://github.com/PryimakIllia/mvc-projekt-pryimak

Poniższe rozdziały uzupełniają dokumentację etapu 1.

## 2.1 Wymagania funkcjonalne

| Identyfikator | Wymaganie |
|---|---|
| WF-01 | Użytkownik otwiera formularz zadania z polami: nazwa, przedmiot i przewidywany czas wykonania w minutach. |
| WF-02 | System sprawdza dane na serwerze i wyświetla komunikaty oraz poprzednio wpisane wartości przy błędach. |
| WF-03 | Po poprawnym zatwierdzeniu formularza użytkownik otrzymuje stronę potwierdzenia przez Post/Redirect/Get. |
| WF-04 | Użytkownik może sprawdzić metodę HTTP, URI, kontekst aplikacji, nagłówek User-Agent i parametry przez /info. |
| WF-05 | Servlet /lifecycle udostępnia licznik żądań GET oraz zapisuje komunikaty cyklu życia w logach. |
| WF-06 | Przykład /forward-demo przekazuje żądanie i atrybut do /info bez zmiany adresu w przeglądarce. |

Etap 2 obejmuje obsługę formularza i potwierdzenie przyjęcia danych, podobnie jak przykład z instrukcji zajęć.
Nie jest jeszcze realizowany trwały zapis zadań ani lista zapisanych rekordów.

## 2.2 Wymagania niefunkcjonalne

- WNF-01: Walidacja jest wykonywana na serwerze niezależnie od kontroli przeglądarki.
- WNF-02: Dane formularza i odpowiedzi są obsługiwane w UTF-8.
- WNF-03: Tekst użytkownika jest kodowany przed umieszczeniem w treści HTML i atrybutach value.
- WNF-04: Odświeżenie potwierdzenia nie powoduje ponownego wysłania POST.
- WNF-05: Dane poszczególnych żądań nie są przechowywane w polach servletu.
- WNF-06: Wspólny licznik wykorzystuje AtomicInteger, aby obsługiwać równoległe żądania.
- WNF-07: Aplikacja korzysta z JDK 21, API jakarta.servlet 6.0 i Tomcata 10.1.

## 6.1 Servlety i obsługa żądań

| Klasa | Adres | Obsługa |
|---|---|---|
| StartServlet | /start | Powitanie GET i formularz POST z etapu 1. |
| RequestInfoServlet | /info | GET: informacje o żądaniu, parametry i wartości wielokrotne. |
| TaskFormServlet | /tasks/add | GET: pusty formularz; POST: walidacja, odtworzenie formularza lub redirect. |
| TaskAddedServlet | /tasks/added | GET: potwierdzenie przyjęcia formularza; brak poprawnego title powoduje HTTP 400. |
| LifecycleServlet | /lifecycle | init, service, doGet, destroy i licznik żądań GET. |
| ForwardDemoServlet | /forward-demo | GET: setAttribute i forward do /info. |

HtmlUtil jest klasą pomocniczą do tworzenia wspólnej otoczki strony i kodowania znaków HTML.
Formularz zawiera trzy pola: title, subject i estimatedMinutes.
Nazwa zadania jest wymagana i może mieć do 120 znaków po usunięciu skrajnych białych znaków.
Przedmiot jest wymagany i może mieć do 80 znaków.
Czas wykonania musi być liczbą całkowitą z zakresu 1–1440 minut.
Oryginalne wpisane wartości są zachowywane przy nieudanej walidacji.

Fragment walidacji z TaskFormServlet:

```java
// Walidacja po stronie serwera; brak lub błędny parametr nie może pominąć kontroli.
if (title.isBlank()) errors.add("Nazwa zadania jest wymagana.");
if (subject.isBlank()) errors.add("Przedmiot jest wymagany.");
try {
    int duration = Integer.parseInt(minutes.strip());
    if (duration < 1 || duration > 1440)
        errors.add("Czas wykonania musi mieścić się w zakresie 1–1440 minut.");
} catch (NumberFormatException e) {
    errors.add("Czas wykonania musi być liczbą całkowitą.");
}
```

Przed getParameter wywoływane jest req.setCharacterEncoding("UTF-8").
Przy błędach servlet ustawia status 400 i ponownie renderuje formularz.
Wartości trafiają do atrybutów value przez HtmlUtil.escape, dzięki czemu np. apostrof
nie przerywa atrybutu, a znaki HTML pozostają tekstem.

Po poprawnej walidacji tytuł jest kodowany przez URLEncoder.encode i dołączany do URL potwierdzenia.
Następnie sendRedirect odsyła odpowiedź 302 z adresem zawierającym req.getContextPath().
Przeglądarka wykonuje nowe GET do /tasks/added. F5 odświeża wyłącznie potwierdzenie.
Potwierdzenie nie jest dowodem trwałego zapisu: na tym etapie nazwa jest przekazywana w URL.

Forward działa wewnątrz serwera, zachowuje jedno żądanie i jego atrybuty, a adres w przeglądarce się nie zmienia.
W ForwardDemoServlet ustawiany jest atrybut forwardNote, następnie forward kieruje żądanie do /info.
RequestInfoServlet odczytuje ten atrybut i pokazuje typ dyspozycji FORWARD.
Redirect tworzy nowe żądanie, więc zwykły atrybut req.setAttribute nie przechodzi do niego.
Po forward i redirect metoda kończy się return; nie dopisuje dalszej odpowiedzi.

LifecycleServlet ma loadOnStartup = 1, dlatego init jest wywoływane przy uruchamianiu aplikacji.
service otrzymuje każde żądanie do tego servletu, zapisuje komunikat i deleguje do super.service.
doGet zwiększa licznik AtomicInteger. destroy zapisuje podsumowanie przy poprawnym zatrzymaniu
lub przeładowaniu aplikacji. Licznik zeruje się przy utworzeniu nowej instancji servletu.
Nagłe zakończenie procesu nie gwarantuje komunikatu destroy.

## 10 Testy ręczne

Wyniki należy uzupełnić po wykonaniu testów na aktualnej wersji WAR.
Adres bazowy: http://localhost:8090/studyplanner/.

| Test | Dane lub działanie | Oczekiwany wynik | Wynik |
|---|---|---|---|
| T-01 | GET /info?przedmiot=MVC&tag=java&tag=tomcat | Informacje o żądaniu oraz obie wartości tag. | Pozytywny |
| T-02 | Puste trzy pola formularza | Komunikaty o brakującej nazwie, przedmiocie i liczbie; bez redirect. | Pozytywny |
| T-03 | Nazwa i przedmiot poprawne, czas abc | Błąd liczby całkowitej, zachowane wszystkie wartości. | Pozytywny |
| T-04 | Czas 0, następnie 1441 | Błąd zakresu 1–1440. | Pozytywny |
| T-05 | Czas 1, następnie 1440 | Dane przechodzą walidację; redirect i GET potwierdzenia. | Pozytywny |
| T-06 | Nazwa zawierająca tylko spacje | Błąd wymaganej nazwy. | Pozytywny |
| T-07 | Zadanie: Ćwiczenia Ілля; przedmiot: MVC; czas: 45 | Poprawne litery, POST 302 i GET potwierdzenia 200. | Pozytywny |
| T-08 | F5 na stronie potwierdzenia | Ponowne GET bez pytania o wysłanie formularza POST. | Pozytywny |
| T-09 | Nazwa <b>test</b>; przedmiot O'Brien; czas abc | Brak interpretacji HTML, zachowane wartości i komunikat walidacji. | Pozytywny |
| T-10 | /lifecycle kilka razy, następnie zatrzymanie Tomcata | Rosnący licznik GET; init, service i destroy w logach. | Pozytywny |
| T-11 | /forward-demo?tag=java | Widok /info, atrybut forwardNote, FORWARD; adres /forward-demo pozostaje. | Pozytywny |
| T-12 | /tasks/added bez title | HTTP 400. | Pozytywny |
| T-13 | Nazwa dłuższa niż 120 lub przedmiot dłuższy niż 80 znaków | Komunikat limitu i zachowanie wartości. | Pozytywny |
| T-14 | GET /start i powitalny POST z etapu 1 | Poprzednia funkcjonalność nadal działa. | Pozytywny |

## 11 Dziennik etapów

Wiersz do dołączenia do dziennika z etapu 1 po zakończeniu testów.

| Data | Tag | Co zrobiono | Problemy |
|2026-10-19|---|---|---|
| [DATA FAKTYCZNEGO ZAKOŃCZENIA] | etap-02 | Dodano formularz zadania z walidacją, zachowaniem wartości i PRG. Dodano /info, cykl życia servletu i przykład forward. Wyniki testów zapisano w rozdziale 10. | Brak nowych problemów. Aplikację uruchomiono na wcześniej skonfigurowanym porcie 8090. |
