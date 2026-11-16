<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Widok strony startowej. Przykłady nagłówka żądania i operatorów EL. --%>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<section class="card">
    <p class="eyebrow">Planowanie nauki</p>
    <h1>Twój plan nauki</h1>
    <p>Dodaj zadanie, wybierz przedmiot i określ przewidywany czas pracy.</p>
    <a class="button" href="${pageContext.request.contextPath}/tasks/add">Dodaj zadanie</a>
</section>
<section class="card">
    <h2>Formularz powitalny</h2>
    <form method="post" action="${pageContext.request.contextPath}/start" accept-charset="UTF-8">
        <label for="name">Jak masz na imię?</label>
        <input id="name" name="name" required maxlength="80" autocomplete="given-name">
        <button type="submit">Przywitaj się</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/start">Sprawdź odpowiedź GET</a></p>
</section>
<section class="card">
    <h2>Informacje techniczne</h2>
    <p>Kontekst aplikacji: <code><c:out value="${pageContext.request.contextPath}"/></code></p>
    <p>Twoja przeglądarka: <c:out value="${header['User-Agent']}" default="Brak nagłówka"/></p>
    <p>Operatory EL: 2 + 3 = ${2 + 3}; 10 &gt; 3: ${10 gt 3}.</p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/info?przedmiot=MVC&amp;tag=java&amp;tag=tomcat">Parametry żądania</a></li>
        <li><a href="${pageContext.request.contextPath}/lifecycle">Cykl życia servletu</a></li>
        <li><a href="${pageContext.request.contextPath}/forward-demo?tag=java">Przekazanie przez forward</a></li>
    </ul>
</section>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
