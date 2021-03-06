<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="locale.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.button.cancel" var="cancel"/>
</head>
<body>
<form action="${pageContext.request.contextPath}/hostel_system" method="get">
    <input type="hidden" name="command" value="CANCEL_REQUEST"/>
    <input type="hidden" name="next-command" value="${param.nextCommand}"/>
    <input type="hidden" name="number" value="${param.page}"/>
    <input type="hidden" name="date" value="${param.date}"/>
    <input type="hidden" name="days" value="${param.days}"/>
    <input type="hidden" name="rooms" value="${param.rooms}"/>
    <input type="hidden" name="hostel" value="${param.hostel}"/>
    <input type="hidden" name="request" value="${param.requestId}"/>
    <input type="hidden" name="id" value="${param.userId}"/>
    <input type="hidden" name="status" value="deleted"/>
    <input type="submit" value="${cancel}"/>
</form>
</body>
</html>