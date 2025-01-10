<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JW 쇼핑</title>
<link rel="stylesheet" href="/resources/css/login.css">
<%
	if (session.getAttribute("id") != null){
		session.invalidate();
	}
%>
</head>
<body>
<div class = "form-container">
	<img src="/resources/img/shopping.png" alt="쇼핑몰">
	<div id="content">
	<form action="/" method="post" autocomplete="off">
		<section id="login" class="login">
			<input type="text" name="id" placeholder="아이디" maxlength="12" required>
			<input type="password" name="password" placeholder="비밀번호" maxlength="12" required>
		</section>
		<footer class="loginAction">
			<button type="submit" class="submit">로그인</button>
			<button type="reset" class="reset">다시 입력</button>
		</footer>
	</form>
	</div>
	
	<a href="signup" id="signup">회원가입</a>
</div>
</body>
</html>