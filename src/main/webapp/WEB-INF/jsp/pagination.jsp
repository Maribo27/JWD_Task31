<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/input_form.css">
</head>
<body>
<div class="input-data-form-help">
    <c:set var = "first" scope = "request" value = "${requestScope.page.first}"/>
    <c:set var = "next" scope = "request" value = "${requestScope.page.next}"/>
    <c:set var = "current" scope = "request" value = "${requestScope.page.current}"/>
    <c:set var = "prev" scope = "request" value = "${requestScope.page.prev}"/>
    <c:set var = "last" scope = "request" value = "${requestScope.page.last}"/>
    <c:choose>
        <c:when test = "${first == last}">
        </c:when>
        <c:when test = "${current == first}">
            <p><c:out value="${current}"/>/<c:out value="${last}"/> <a href="${requestScope.page.nextPage}"><c:out value="${next}"/></a> ... <a href="${requestScope.page.lastPage}"><c:out value="${last}"/></a></p>
        </c:when>
        <c:when test = "${current == last}">
            <p><a href="${requestScope.page.firstPage}">1</a> ... <a href="${requestScope.page.prevPage}"><c:out value="${prev}"/></a> <c:out value="${current}"/>/<c:out value="${last}"/></p>
        </c:when>

        <c:otherwise>
            <p><a href="${requestScope.page.firstPage}">1</a> <a href="${requestScope.page.prevPage}"><c:out value="${prev}"/></a> <c:out value="${current}"/>/<c:out value="${last}"/> <a href="${requestScope.page.nextPage}"><c:out value="${next}"/></a> <a href="${requestScope.page.lastPage}"><c:out value="${last}"/></a></p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
