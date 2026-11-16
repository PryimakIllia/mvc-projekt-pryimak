<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Komunikat powitalny przekazany przez StartServlet. --%>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<section class="card">
    <h1><c:out value="${requestScope.heading}"/></h1>
    <p><c:out value="${requestScope.message}"/></p>
    <p>Metoda żądania: <c:out value="${pageContext.request.method}"/></p>
    <a class="button" href="${pageContext.request.contextPath}/">Powrót do strony głównej</a>
</section>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
