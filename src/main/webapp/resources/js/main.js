// AJAX 요청 후 로그아웃 버튼 클릭 이벤트 다시 바인딩
function bindLogoutButton() {
    $(document).off('click', '#logout').on('click', '#logout', function(event) {
    	event.preventDefault();  // 기본 동작 방지
        event.stopImmediatePropagation()
        $.ajax({
            url: 'http://localhost:8080/logout',
            type: 'POST',
            success: function(response) {
                alert('로그아웃되었습니다.');
                window.location.href = '/';  // 로그아웃 후 홈으로 리다이렉트
            },
            error: function() {
                alert('로그아웃에 실패했습니다.');
            }
        });
    });
}

// AJAX 요청 성공 후 항상 로그아웃 이벤트 핸들러 재등록
function onAjaxSuccess(data) {
    $('#main-content').html(data);
    bindLogoutButton(); // 로그아웃 버튼 이벤트 재등록
}

// 페이지 로딩 시, 기본적으로 전체 상품 보기 로드
$(document).ready(function() {
    // 로그인 상태 체크 함수
    function checkLogin() {
        var loggedUser = '<%= sessionScope.loggedUser != null ? "true" : "false" %>';
        if (loggedUser === 'false') {
            alert("로그인이 필요합니다.");
            window.location.href = "/";  // 로그인 페이지로 리다이렉트
            return false;
        }
        return true;
    }

    window.onpopstate = function(event) {
        if (!checkLogin()) return;

        if (event.state) {
            if (event.state.page === 'all') {
                loadAllProducts();
            } else if (event.state.page === 'home') {
                $('#main-content').html(''); // 메인 콘텐츠 초기화
                loadAllProducts(); // 메인 페이지 로드
            } else if (event.state.page) {
                loadCategoryProducts(event.state.page);
            }
        } else {
            // 상태가 없을 경우 기본 메인 페이지로 복원
            $('#main-content').html('');
            loadAllProducts();
        }
    };

    // 전체 상품 보기 클릭 시 AJAX 요청
    window.loadAllProducts = function(event) {
        if (!checkLogin()) return;
        if (event) {
            event.preventDefault();
            // URL 변경 (전체 상품 보기)
            history.pushState({ page: 'all' }, '', '/product/all');
        }

        // 메인 콘텐츠 초기화
        $('#main-content').html('<p>상품을 불러오는 중...</p>');

        // AJAX 요청
        $.ajax({
            url: '/product/all',
            type: 'GET',
            success: function(data) {
                onAjaxSuccess(data);
            },
            error: function() {
                $('#main-content').html('<p>전체 상품 목록을 불러오는 데 실패했습니다.</p>');
            }
        });
    }

    // 특정 카테고리 상품 보기 클릭 시 AJAX 요청
    function loadCategoryProducts(category) {
        if (!checkLogin()) return;

        // URL 변경 (카테고리별 상품 보기)
        history.pushState({ page: category }, '', '/product/' + category);

        // 메인 콘텐츠 초기화
        $('#main-content').html('<p>상품을 불러오는 중...</p>');

        // AJAX 요청
        $.ajax({
            url: '/product/' + category,
            type: 'GET',
            success: function(data) {
                onAjaxSuccess(data);
            },
            error: function() {
                $('#main-content').html('<p>상품 목록을 불러오는 데 실패했습니다.</p>');
            }
        });
    }

    // 페이지 로딩 시, 기본적으로 전체 상품 보기 로드
    loadAllProducts();

    // 전체 상품 보기 클릭 이벤트 핸들러
    $('h3[onclick="loadAllProducts(event)"]').on('click', function(event) {
        loadAllProducts(event);
    });

    // 카테고리 링크 클릭 이벤트 핸들러
    $(document).on('click', '.category-link', function(event) {
        event.preventDefault(); // 기본 동작 방지
        const category = $(this).data('category');
        loadCategoryProducts(category);
    });

    // 상품 추가 버튼 클릭 이벤트 핸들러
    $('#addProduct').on('click', function(event) {
        event.preventDefault();
        if (!checkLogin()) return;

        // #main-content 초기화
        $('#main-content').html('<p>상품 추가 페이지를 불러오는 중...</p>');
        history.pushState({ page: 'addProduct' }, '', '/addProduct');

        // AJAX 요청
        $.ajax({
            url: '/addProduct',
            type: 'GET',
            success: function(data) {
                onAjaxSuccess(data);

                // CSS 동적으로 추가 (중복 방지 및 로드 확인)
                const cssUrl = '/resources/css/addProduct.css';
                if (!$('link[href="' + cssUrl + '"]').length) {
                    const link = $('<link rel="stylesheet" type="text/css" href="' + cssUrl + '">');
                    link.on('load', function() {
                        console.log('CSS 파일 로드 완료');
                    }).on('error', function() {
                        console.error('CSS 파일 로드 실패');
                    });
                    $('head').append(link);
                }
            },
            error: function() {
                $('#main-content').html('<p>상품 추가 페이지를 불러오는 데 실패했습니다.</p>');
            }
        });
    });

    // 로그아웃 버튼 클릭 이벤트 핸들러 초기 바인딩
    bindLogoutButton();

});
