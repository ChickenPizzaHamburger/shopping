<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.jw.shopping.dto.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JW 쇼핑 : 쇼핑은 JW 쇼핑</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <c:if test="${empty sessionScope.loggedUser}">
        <script>
            alert("로그인이 필요합니다.");
            window.location.href = "/";  // 로그인 페이지로 리다이렉트
        </script>
    </c:if>
    <style>
        /* 로고 위치 왼쪽 상단에 고정 */
        #logo-link {
            position: absolute;
            top: 10px;
            left: 10px;
        }

        #logo {
            width: 100px; /* 로고 크기 조정 */
            height: auto;
        }
    </style>
</head>
<script src="/resources/js/main.js"></script>
<script src="/resources/js/write.js"></script>
<body>
    <header>
        <div>
            <!-- 로고 추가: 클릭 시 페이지 새로고침 -->
            <a href="#" id="logo-link">
                <img src="/resources/img/logo.png" alt="JW 쇼핑 로고" id="logo">
            </a>

            <!-- 로그인한 사용자 정보 표시 -->
            <c:if test="${not empty sessionScope.loggedUser}">
           		<div class="user-container">
                	<span>안녕하세요, ${sessionScope.loggedUser.name}님!</span>
                    <button id="logout" type="submit">로그아웃</button>
                </div>
            </c:if>
        </div>
    </header>

    <nav>
        <div class="search-bar">
            <input type="text" placeholder="검색...">
            <button type="submit">검색</button>
        </div>
        <div>
            <a href="mypage">마이페이지</a>
            <a href="purchase">구매하기</a>
            <a href="cart">장바구니</a>
        </div>
    </nav>

    <aside>
        <h3 onclick="loadAllProducts(event)" style="cursor:pointer;">전체 상품 보기</h3>
        <ul>
            <li><a href="/product/clothing-accessories" class="category-link" data-category="clothing-accessories">의류/악세서리</a></li>
            <li><a href="/product/cosmetics" class="category-link" data-category="cosmetics">화장품</a></li>
            <li><a href="/product/food" class="category-link" data-category="food">식품</a></li>
            <li><a href="/product/books-music" class="category-link" data-category="books-music">도서/음반</a></li>
            <li><a href="/product/travel-tickets" class="category-link" data-category="travel-tickets">여행/티켓</a></li>
            <li><a href="/product/baby-products" class="category-link" data-category="baby-products">육아용품</a></li>
            <li><a href="/product/kitchen-products" class="category-link" data-category="kitchen-products">주방용품</a></li>
            <li><a href="/product/home-products" class="category-link" data-category="home-products">생활용품</a></li>
            <li><a href="/product/interior" class="category-link" data-category="interior">인테리어</a></li>
            <li><a href="/product/electronics" class="category-link" data-category="electronics">가전제품</a></li>
            <li><a href="/product/hobbies" class="category-link" data-category="hobbies">취미용품</a></li>
            <li><a href="/product/vehicles" class="category-link" data-category="vehicles">탈것용품</a></li>
            <li><a href="/product/toys-stationery" class="category-link" data-category="toys-stationery">완구/문구</a></li>
            <li><a href="/product/pet-products" class="category-link" data-category="pet-products">펫용품</a></li>
            <li><a href="/product/health" class="category-link" data-category="health">헬스/건강</a></li>
        </ul>

        <c:if test="${not empty sessionScope.loggedUser and sessionScope.loggedUser.role.value <= 100}">
            <a href="#" id="addProduct" class="admin-button">상품 추가</a>
        </c:if>
    </aside>

    <main id="main-content"></main>

    <footer>
        <a href="jwcorp">회사소개</a> |
        <a href="recruit">인재채용</a> |
        <a href="proposal">제휴제안</a> |
        <a href="service">이용약관</a> |
        <a href="privacy">개인정보처리방침</a> |
        <a href="help">고객센터</a> | &copy; JW Corp.
    </footer>

    <script>
        // 로고 클릭 시 페이지 새로고침
        $('#logo-link').on('click', function(event) {
            event.preventDefault();  // 기본 동작 방지
            history.pushState({ page: 'home' }, null, '/');  // 주소를 /로 변경
            $('#main-content').html(''); // 메인 콘텐츠 초기화
            loadAllProducts();  // 전체 상품 보기 로드
        });
    </script>
</body>
</html>
