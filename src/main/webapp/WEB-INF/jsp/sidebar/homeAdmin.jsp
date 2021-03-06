<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/input_form.css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="locale.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.button.show.users" var="users"/>
    <fmt:message bundle="${loc}" key="locale.button.show.hostels" var="hostels"/>
    <fmt:message bundle="${loc}" key="locale.button.show.requests" var="requests"/>
    <fmt:message bundle="${loc}" key="locale.button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="locale.message.greetings" var="greetings"/>
    <fmt:message bundle="${loc}" key="locale.name.administrator" var="name"/>
</head>
<body>
<div id="sidebar">
    <section class="container">
        <div class="input-data-form">
            <h3>${greetings}, ${name}!</h3>
            <hr>
            <form action="${pageContext.request.contextPath}/hostel_system" method="get">
                <input type="hidden" name="number" value="1"/>
                <input type="hidden" name="command" value="SHOW_USERS"/>
                <input type="submit" value="${users}"/>
            </form>
            <form action="${pageContext.request.contextPath}/hostel_system" method="get">
                <input type="hidden" name="number" value="1"/>
                <input type="hidden" name="command" value="SHOW_HOSTELS"/>
                <input type="submit" value="${hostels}"/>
            </form>
            <form action="${pageContext.request.contextPath}/hostel_system" method="get">
                <input type="hidden" name="number" value="1"/>
                <input type="hidden" name="command" value="SHOW_REQUESTS"/>
                <input type="submit" value="${requests}"/>
            </form>
            <form action="${pageContext.request.contextPath}/hostel_system" method="get">
                <input type="hidden" name="command" value="LOGOUT"/>
                <input type="submit" value="${logout}"/>
            </form>
        </div>
    </section>
</div>
</body>
</html>