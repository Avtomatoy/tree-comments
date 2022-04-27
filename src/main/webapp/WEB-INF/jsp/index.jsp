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
    <div>
      <h3>${pageContext.request.userPrincipal.name}</h3>
      <a href="/themes">Темы</a>
      <sec:authorize access="!isAuthenticated()">
        <h4><a href="/login">Войти</a></h4>

      </sec:authorize>
      <sec:authorize access="isAuthenticated()">
        <h4><a href="/registration">Зарегистрировать новых модераторов</a></h4>
        <h4><a href="/logout">Выйти</a></h4>
      </sec:authorize>
    </div>
</body>
</html>
