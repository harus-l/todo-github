<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/WebContent/css/style.css">

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TodoList</title>
</head>
<body>
	<div class="container">
		<h1>Todoリスト</h1>
		<form action="/Main" method="post">
			<!-- テキスト入力欄 -->
			<div class="input">
				Todo：<input type="text" name="text">
				<button type="submit" name="action" value="create">追加</button>
				<c:if test="${not empty errorMsg}">
					<p>${errorMsg}</p>
				</c:if>
				<c:if test="${not empty defMsg}">
					<p>${defMsg}</p>
				</c:if>
			</div>
			
			<!-- リスト追加欄 -->
			<div class="list">
				<c:forEach var="todo" items="${todoList}">
				<p><label><input type="checkbox" name="check" value="${todo.id}">
				<c:out value="${todo.text}" /></label></p>
				</c:forEach>
			</div>
			
			<div class="delete">	
				<button type="submit" name="action" value="delete">消去</button>
			</div>
		</form>
	</div>
</body>
</html>