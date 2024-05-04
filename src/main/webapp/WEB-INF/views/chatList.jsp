<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "root" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${root}/chat/createRoom" method="post">
    	<input type="text" name="name" placeholder="채팅방 이름">
    	<button type="submit" >방 만들기</button>
	</form>
	
	<table>
		<c:forEach items="${list}" var="item" varStatus="index">
			<tr>
				<td>
					<a href="${root}/chat/chatRoom/${item.roomId}">${index} : ${item.name}</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>