<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="ahs" uri="hostelTag" %>
<html>
<head>
    <c:set var = "currentPage" scope = "session" value = "jsp/usersInfoPage.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/carousel.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/input_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navigation_bar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.png">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="locale.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.title.users" var="users"/>
    <fmt:message bundle="${loc}" key="locale.table.title.user.id" var="id"/>
    <fmt:message bundle="${loc}" key="locale.table.title.username" var="username"/>
    <fmt:message bundle="${loc}" key="locale.table.title.email" var="email"/>
    <fmt:message bundle="${loc}" key="locale.table.title.name" var="name"/>
    <fmt:message bundle="${loc}" key="locale.table.title.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="locale.table.title.lastname" var="lastname"/>
    <fmt:message bundle="${loc}" key="locale.table.title.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="locale.table.title.status" var="status"/>
    <fmt:message bundle="${loc}" key="locale.table.title.reason" var="reason"/>
    <fmt:message bundle="${loc}" key="locale.table.title.block.date" var="blockDate"/>
    <fmt:message bundle="${loc}" key="locale.table.title.unlock.date" var="unlockDate"/>
    <fmt:message bundle="${loc}" key="locale.table.title.action" var="action"/>
    <fmt:message bundle="${loc}" key="locale.button.block" var="block"/>
    <fmt:message bundle="${loc}" key="locale.button.unlock" var="unlock"/>
    <title> ${users} | ${sessionScope.user.username} | Hostel System</title>
</head>
<body>

<div style="padding:20px;"></div>
<div class="table-container">
    <table>
        <tr>
            <th>${id}</th>
            <th>${username}</th>
            <th>${email}</th>
            <th>${name}</th>
            <th>${surname}</th>
            <th>${lastname}</th>
            <th>${discount}</th>
            <th>${status}</th>
            <th>${reason}</th>
            <th>${blockDate}</th>
            <th>${unlockDate}</th>
            <th>${action}</th>
        </tr>
        <c:forEach items="${requestScope.users}" var="user">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.username}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.surname}"/></td>
                <td><c:out value="${user.lastname}"/></td>
                <td><c:out value="${user.discount}"/></td>
                <td><ahs:user-status userStatus="${user.status}"/></td>
                <td><c:out value="${user.blockReason}"/></td>
                <td><c:out value="${user.blockDate}"/></td>
                <td><c:out value="${user.unlockDate}"/></td>
                <td>
                    <c:choose>
                        <c:when test = "${user.status eq 'BANNED'}">
                            <form action="${pageContext.request.contextPath}/hostel_system" method="get">
                                <input type="hidden" name="command" value="UNLOCK"/>
                                <input type="hidden" name="id" value="${user.id}"/>
                                <input type="submit" value="${unlock}"/>
                            </form>
                        </c:when>
                        <c:when test = "${user.status eq 'USER'}">
                            <form action="${pageContext.request.contextPath}/hostel_system" method="get">
                                <input type="hidden" name="command" value="OPEN_BLOCK_PAGE"/>
                                <input type="hidden" name="id" value="${user.id}"/>
                                <input type="submit" value="${block}"/>
                            </form>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<jsp:include page="/WEB-INF/jsp/pagination.jsp"/>
<jsp:include page="/WEB-INF/jsp/header/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

</body>
</html>
