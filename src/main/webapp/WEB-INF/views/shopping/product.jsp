<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.jw.shopping.dto.Product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JW 쇼핑 : 상품 리스트</title>
    <link rel="stylesheet" href="/resources/css/product.css?v=1.0">
</head>
<script src="/resources/js/product.js"></script>
<body>
    <!-- 상품 목록 -->
	<div class="product-list">
	    <c:forEach var="product" items="${products}">
	        <div class="product">
	            <a href="/product/detail/${product.id}" class="product-detail-link" data-id="${product.id}">
				    <img src="${product.imagePath}" alt="${product.name}" class="product-image"/>
				    <h2 class="product-name">${product.name}</h2>
				    <p class="product-price">
				        <fmt:formatNumber value="${product.price}" type="number" pattern="#,###" />원
				    </p>
				</a>
	        </div>
	    </c:forEach>
	</div>

    <!-- 페이지네이션 -->
    <c:if test="${totalPages > 1}">
	    <div class="pagination">
	        <c:forEach var="i" begin="1" end="${totalPages}">
	            <a href="#" class="${i == currentPage ? 'active' : ''}" onclick="loadPage(${i})">${i}</a>
	        </c:forEach>
	    </div>
	</c:if>

    <script>
        // 페이지네이션 클릭 시 AJAX 요청
        function loadPage(page) {
	        let category = "${category}";  // 현재 카테고리 정보를 저장
	        let url = category === "all" ? '/product/all?page=' + page : '/product/' + category + '?page=' + page;
	        
	        $.ajax({
	            url: url,
	            type: 'GET',
	            success: function(response) {
	                $('#main-content').html(response);  // 상품 목록 갱신
	            },
	            error: function() {
	                $('#main-content').html('<p>상품 목록을 불러오는 데 실패했습니다.</p>');
	            }
	        });
	    }
    </script>
</body>
</html>
