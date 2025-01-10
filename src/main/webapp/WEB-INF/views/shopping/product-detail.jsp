<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JW 쇼핑 : ${product.name}</title>
</head>
<script src="/resources/js/detail.js"></script>
<body>
    <main class="product-detail">
        <aside>
            <img src="${product.imagePath}" alt="${product.name}" class="product-detail-image"/>
        </aside>

        <h1 class="product-detail-name">${product.name}</h1>
        <p class="product-detail-price">
            <fmt:formatNumber value="${product.price}" type="number" pattern="#,###" />원
        </p>
        <button class="add-to-cart">장바구니에 담기</button>
    </main>

	<!-- 게시판 영역 -->
    <section id="board-section">
        <h2>리뷰 게시판</h2>
        <article id="review-board"></article>

        <h2>Q&A 게시판</h2>
        <article id="qna-board"></article>
    </section>
    
    <footer id="content"></footer>
</body>
</html>
