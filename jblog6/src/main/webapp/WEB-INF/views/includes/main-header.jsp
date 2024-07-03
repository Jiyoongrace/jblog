<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h1 class="logo" style="background:url(${pageContext.request.contextPath}/assets/images/logo.jpg)">JBlog</h1>
<ul class="menu">

        <sec:authorize access="!isAuthenticated()">
            <li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
            <li><a href="${pageContext.request.contextPath}/user/join">회원가입</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
            <sec:authentication property="principal.username" var="username" />
            <li><a href="${pageContext.request.contextPath}/${username}">내블로그</a></li>
        </sec:authorize>
</ul>