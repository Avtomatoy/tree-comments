<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
  <title>Главная</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<c:forEach items="${allThemes}" var="theme">
        <div>
            <a href="themes/${theme.id}">${theme.name}</a>
        </div>
</c:forEach>
    <div>
        <form:form method="POST" modelAttribute="themeForm" action="themes">
            <div>
                Ваше имя:
                <form:textarea path="username" required="true" rows="1" columns="100"></form:textarea>
            </div>
            <div>
                Название темы:
                <form:textarea path="name" required="true" rows="1" columns="100"></form:textarea>
            </div>
            <div>
                Текст сообщения:
                <form:textarea path="messageText" required="true" rows="1" columns="100"></form:textarea>
            </div>
            <button type="submit">Создать тему</button>
        </form:form>
    </div>

    <a href="/">Back</a>
</body>
</html>
