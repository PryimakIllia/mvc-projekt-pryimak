<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Widok potwierdzenia GET. Nazwa zadania i licznik pochodzą z sesji. --%>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<section class="card">
    <c:choose>
        <c:when test="${empty sessionScope.lastTitle}">
            <h1>Brak potwierdzenia</h1>
            <p>Najpierw prześlij poprawnie wypełniony formularz zadania.</p>
        </c:when>
        <c:otherwise>
            <h1>Formularz przyjęty</h1>
            <p>Ostatnie zadanie: <strong><c:out value="${sessionScope.lastTitle}"/></strong>.</p>
            <p>Potwierdzenie dotyczy bieżącej sesji. Zadanie nie jest zapisywane w bazie danych.</p>
        </c:otherwise>
    </c:choose>
    <a class="button" href="${pageContext.request.contextPath}/tasks/add">Dodaj kolejne zadanie</a>
</section>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
