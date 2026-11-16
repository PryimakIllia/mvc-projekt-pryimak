<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Formularz odtwarza parametry POST i wyświetla błędy z atrybutu żądania. --%>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<section class="card form-card">
    <p class="eyebrow">Zadania / Nowe zadanie</p>
    <h1>Dodaj zadanie</h1>
    <p class="intro">Podaj nazwę, przedmiot i przewidywany czas wykonania.</p>
    <c:if test="${not empty requestScope.errors}">
        <p class="alert" role="alert">Popraw dane. Liczba błędów: ${requestScope.errors.size()}.</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/tasks/add" accept-charset="UTF-8">
        <div class="field">
            <label for="title">Nazwa zadania</label>
            <input id="title" name="title" aria-invalid="${not empty requestScope.errors.title}"
                   aria-describedby="title-error" value="<c:out value='${param.title}'/>">
            <span id="title-error" class="error"><c:out value="${requestScope.errors.title}"/></span>
        </div>
        <div class="field">
            <label for="subject">Przedmiot</label>
            <input id="subject" name="subject" aria-invalid="${not empty requestScope.errors.subject}"
                   aria-describedby="subject-error" value="<c:out value='${param.subject}'/>">
            <span id="subject-error" class="error"><c:out value="${requestScope.errors.subject}"/></span>
        </div>
        <div class="field">
            <label for="estimatedMinutes">Czas wykonania w minutach</label>
            <input id="estimatedMinutes" name="estimatedMinutes" inputmode="numeric"
                   aria-invalid="${not empty requestScope.errors.estimatedMinutes}"
                   aria-describedby="minutes-hint minutes-error" value="<c:out value='${param.estimatedMinutes}'/>">
            <span id="minutes-hint" class="hint">Podaj liczbę całkowitą od 1 do 1440.</span>
            <span id="minutes-error" class="error"><c:out value="${requestScope.errors.estimatedMinutes}"/></span>
        </div>
        <button type="submit">Zatwierdź zadanie</button>
    </form>
</section>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
