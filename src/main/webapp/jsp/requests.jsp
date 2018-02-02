<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ahs" uri="hostelTag" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/input_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.png">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="locale.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.title.requests" var="requests"/>
    <fmt:message bundle="${loc}" key="locale.table.title.request.id" var="requestId"/>
    <fmt:message bundle="${loc}" key="locale.table.title.user.id" var="userId"/>
    <fmt:message bundle="${loc}" key="locale.table.title.hostel.id" var="hostelId"/>
    <fmt:message bundle="${loc}" key="locale.table.title.type" var="type"/>
    <fmt:message bundle="${loc}" key="locale.table.title.rooms.count" var="rooms"/>
    <fmt:message bundle="${loc}" key="locale.table.title.days.count" var="days"/>
    <fmt:message bundle="${loc}" key="locale.table.title.cost" var="cost"/>
    <fmt:message bundle="${loc}" key="locale.table.title.date" var="date"/>
    <fmt:message bundle="${loc}" key="locale.table.title.status" var="status"/>
    <fmt:message bundle="${loc}" key="locale.table.title.action" var="action"/>
    <title> ${requests} | ${sessionScope.user.username} | Hostel System</title>
</head>

<body>
<div style="padding:20px;"></div>
<div class="table-container">
    <c:if test="${fn:length(requestScope.requests) gt 0}">
        <table>
        <tr>
            <th>${requestId}</th>
            <th>${userId}</th>
            <th>${hostelId}</th>
            <th>${type}</th>
            <th>${rooms}</th>
            <th>${days}</th>
            <th>${cost}</th>
            <th>${date}</th>
            <th>${status}</th>
            <th>${action}</th>
        </tr>
        <c:forEach items="${requestScope.requests}" var="request">
            <tr>
                <td><c:out value="${request.id}"/></td>
                <td><c:out value="${request.userId}"/></td>
                <td><c:out value="${request.hostelId}"/></td>
                <td><c:out value="${request.type}"/></td>
                <td><c:out value="${request.room}"/></td>
                <td><c:out value="${request.days}"/></td>
                <td><c:out value="${request.cost}"/></td>
                <td><c:out value="${request.date}"/></td>
                <td><ahs:request-status requestStatus="${request.status}"/></td>
                <td>
                    <c:set var = "page" scope="page" value = "/WEB-INF/jsp/button/requestsUser.jsp"/>
                    <c:if test = "${sessionScope.user.status eq 'ADMIN'}">
                        <c:set var = "page" scope="page" value = "/WEB-INF/jsp/button/requestsAdmin.jsp"/>
                    </c:if>
                    <c:if test = "${request.status eq 'PROCESSING'}">
                        <jsp:include page="${page}">
                            <jsp:param name="page" value="${requestScope.page.current}" />
                            <jsp:param name="userId" value="${sessionScope.user.id}" />
                            <jsp:param name="requestId" value="${request.id}" />
                        </jsp:include>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    </c:if>
</div>

<jsp:include page="/WEB-INF/jsp/pagination.jsp"/>
<jsp:include page="/WEB-INF/jsp/header/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>