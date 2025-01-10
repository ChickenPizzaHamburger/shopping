<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 추가</title>
    <script src="/resources/js/addProduct.js" defer></script>
</head>
<body>
    <header>
        <h1>상품 추가</h1>
    </header>

    <form action="addProduct" method="post" enctype="multipart/form-data" onsubmit="return validateForm()" autocomplete="off">
        <div>
            <label for="productName">상품 이름 (5자 이상, 100자 이내): </label>
            <input type="text" id="productName" name="productName" maxlength="100" required>
        </div>

        <div>
            <label for="productPrice">상품 가격 (10원 이상): </label>
            <input type="number" id="productPrice" name="productPrice" min="10" required>
        </div>

        <div>
            <label for="productNumber">상품 갯수 (1개 이상): </label>
            <input type="number" id="productNumber" name="productNumber" min="1" required>
        </div>

        <div>
            <label for="productImage">상품 이미지 첨부: </label>
            <input type="file" id="productImage" name="productImage" accept="image/*" required>
        </div>

        <div>
            <label for="productCategory">상품 카테고리: </label>
            <select id="productCategory" name="productCategory" required>
                <option value="1">의류/악세서리</option>
                <option value="2">화장품</option>
                <option value="3">식품</option>
                <option value="4">도서/음반</option>
                <option value="5">여행/티켓</option>
                <option value="6">육아용품</option>
                <option value="7">주방용품</option>
                <option value="8">생활용품</option>
                <option value="9">인테리어</option>
                <option value="10">가전제품</option>
                <option value="11">취미용품</option>
                <option value="12">탈것용품</option>
                <option value="13">완구/문구</option>
                <option value="14">펫용품</option>
                <option value="15">헬스/건강</option>
            </select>
        </div>

        <div>
            <button type="submit">상품 등록</button>
            <a href="/">메인으로 돌아가기</a>
        </div>
    </form>
</body>
</html>