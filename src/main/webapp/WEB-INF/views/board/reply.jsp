<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<form action="reply" method="post">
			<input type="hidden" name="bId" value="${reply.bId}">
			<input type="hidden" name="bGroup" value="${reply.bGroup}">
			<input type="hidden" name="bStep" value="${reply.bStep}">
			<input type="hidden" name="bIndent" value="${reply.bIndent}">
			<tr>
				<td> 번호 </td>
				<td> ${reply.bId} </td>
			</tr>
			<tr>
				<td> 히트 </td>
				<td> ${reply.bHit} </td>
			</tr>
			<tr>
				<td> 이름 </td>
				<td> <input type="text" name="bName" value="${reply.bName}"></td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" name="bTitle" value="${reply.bTitle}"></td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> <textarea rows="10"  name="bContent">${reply.bContent}</textarea></td>
			</tr>
			<tr >
				<td colspan="2"><input type="submit" value="답변"> <a href="list" >목록</a></td>
			</tr>
		</form>
	</table>
	
</body>
</html> 