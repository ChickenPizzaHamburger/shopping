<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판</title>
</head>
<script src="resources/js/write.js"></script>
<body>

    <table width="500" cellpadding="0" cellspacing="0" border="1">
        <tr>
            <td colspan="2">제목</td>
            <td>작성자</td>
            <td>작성일</td>
            <td>조회</td>
        </tr>

        <c:forEach items="${list}" var="dto">
            <tr>
                <td>${dto.bId}</td>
                <td>
                    <!-- bIndent 값만큼 공백을 추가하고, 그 뒤에 '└' 기호와 제목을 표시 -->
                    <c:if test="${dto.bIndent > 0}">
                        <c:forEach begin="0" end="${dto.bIndent - 1}" varStatus="status">
                            <span>&nbsp;&nbsp;&nbsp;&nbsp;</span> <!-- 4개의 공백을 사용 -->
                        </c:forEach>
                        <span>└</span>
                    </c:if>
                    <a href="content?bId=${dto.bId}">${dto.bTitle}</a>
                </td>
                <td>${dto.bName}</td>
                <td>${dto.bDate}</td>
                <td>${dto.bHit}</td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="5"> <a href="../write" class="write">글작성</a> </td>
        </tr>
        <br>
    </table>

</body>
</html>