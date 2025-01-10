$(document).ready(function () {
    // 상품 상세 페이지 로드 함수
    function loadProductDetail(productId) {
        console.log('상품 상세 로드:', productId);

        // 로그인 여부 확인
        if (!checkLogin()) return;

        // URL 변경
        history.pushState({ page: 'productDetail', id: productId }, '', '/product/detail/' + productId);

        // 메인 콘텐츠 초기화
        $('#main-content').html('<p>상품 상세 정보를 불러오는 중...</p>');

        // AJAX 요청
        $.ajax({
            url: '/product/detail/' + productId,
            type: 'GET',
            success: function (data) {
                $('#main-content').html(data);
            },
            error: function () {
                $('#main-content').html('<p>상품 상세 정보를 불러오는 데 실패했습니다.</p>');
            }
        });
    }

    // 상품 상세 링크 클릭 이벤트 처리
    $(document).on('click', '.product-detail-link', function (event) {
        event.preventDefault(); // 기본 동작 방지
        const productId = $(this).data('id'); // 클릭한 상품 ID 가져오기
        loadProductDetail(productId); // 상품 상세 정보 로드
    });

    // 브라우저의 뒤로 가기 버튼 처리
    window.onpopstate = function (event) {
        if (!checkLogin()) return;

        if (event.state) {
            if (event.state.page === 'productDetail') {
                loadProductDetail(event.state.id); // 상품 상세 페이지 로드
            } else if (event.state.page === 'all') {
                loadAllProducts(); // 전체 상품 로드
            } else if (event.state.page) {
                loadCategoryProducts(event.state.page); // 카테고리 상품 로드
            }
        }
    };
    
    // 로그인 여부 확인 함수
    function checkLogin() {
        // 실제 로그인 여부 확인 로직을 작성하세요
        const isLoggedIn = true; // 여기를 실제 로그인 상태로 변경
    
        if (!isLoggedIn) {
            alert('로그인이 필요합니다.');
            window.location.href = '/login'; // 로그인 페이지로 리다이렉트
            return false;
        }
        return true;
    }
});
