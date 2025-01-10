<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>JW 쇼핑 : 회원가입</title>
<link rel="stylesheet" href="/resources/css/signup.css">
<%
	request.setCharacterEncoding("UTF-8");

	if (session.getAttribute("id") != null){
		session.invalidate();
	}

	java.util.Calendar calendarMin = java.util.Calendar.getInstance();
	calendarMin.add(java.util.Calendar.YEAR, -120);
	String minDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(calendarMin.getTime());
	
	java.util.Calendar calendarMax = java.util.Calendar.getInstance();
	calendarMax.add(java.util.Calendar.YEAR, -10);
	String maxDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(calendarMax.getTime());
%>
<script src="/resources/js/signup.js"></script>
</head>
<body>
	<nav class="login">
		이미 가입하셨나요? <a href="/">로그인 →</a>
	</nav>
    <form action="signup" method="post" id="signupForm" onsubmit="return signup();" autocomplete="off">
        <table>
            <thead>
                <tr>
                    <th>회원가입</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                	<td>
                		<small id="userIdError" class="error-message">${errorId != null ? errorId : ''}</small>
                    	<label for="id">아이디:</label>
            			<input type="text" id="id" name="id" size="12" maxlength="12" placeholder="4~12자의 영문과 숫자" required>
            		</td>
                </tr>
                <tr>
                    <td>
                    	<small id="userPwdError" class="error-message">${errorPwd != null ? errorPwd : ''}</small>
			            <label for="password">비밀번호:</label>
			            <input type="password" id="password" name="password" size="12" placeholder="8자 이상의 영문과 숫자, 특수문자 조합" required>
			        </td>
                </tr>
                <tr>
                     <td>
			            <label for="pwdChecked">비밀번호 확인:</label>
			            <input type="password" id="pwdChecked" size="12" placeholder="8자 이상의 영문과 숫자, 특수문자 조합" required>
			        </td>
                </tr>
                <tr>
                    <td>
                    	<small id="userEmailError" class="error-message">${errorEmail != null ? errorEmail : ''}</small>
			            <label for="email">메일 주소:</label>
			            <input type="email" id="email" name="email" placeholder="예) admin@shopping.com" required>
			        </td>
                </tr>
                <tr>
                    <td>
                    	<small id="userNameError" class="error-message">${errorName != null ? errorName : ''}</small>
			            <label for="name">이름:</label>
			            <input type="text" id="name" name="name" placeholder="한글로 입력해주세요." required>
			        </td>
                </tr>
                <tr>
                	<td>
			            <label>성별:</label>
			            <input type="radio" id="male" name="sex" value="MALE">
			            <label for="male">남성</label>
			            <input type="radio" id="female" name="sex" value="FEMALE">
			            <label for="female">여성</label>
			        </td>
                </tr>
                <tr>
                    <td>
			            <label for="birthday">생일:</label>
			            <input type="date" id="birthday" name="birthday"  min="<%= minDate %>" max="<%= maxDate %>" value="2001-01-01" required>
			        </td>
                </tr>
            </tbody>
        </table>
        <footer>
            <input type="submit" value="회원 가입">
            <input type="reset" value="다시 입력">
        </footer>
    </form>
</body>
</html>