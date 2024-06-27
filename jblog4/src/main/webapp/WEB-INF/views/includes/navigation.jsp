<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">

<div id="navigation">
    <h2> 카테고리 </h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/${blogVo.id}">전체보기</a></li>
        <c:forEach items="${categoryList }" var="vo" varStatus="status">
            <li><a href="${pageContext.request.contextPath}/${blogVo.id}/${vo.no}">${vo.name}</a></li>
        </c:forEach>
    </ul>
</div>