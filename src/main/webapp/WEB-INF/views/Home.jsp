<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Hello, Spring Boot App<br>
	<a href="include.do">인클루드 테스트</a>
	
		<br><hr><br>
		<c:if test="${currentUser != null}">	
		${currentUser}
		</c:if>
	<br><hr><br>
		
	
	<a href="signin">sign in</a><br>
	<a href="signup">sign up</a><br>
	<a href="resetpassword">reset Password</a><br>
	<a href="edituserinfo">edit User Info</a><br>
	
	
</body>
</html>