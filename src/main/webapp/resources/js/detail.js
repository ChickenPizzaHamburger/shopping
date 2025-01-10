// review, qna 게시판 데이터 로드
function loadBoardData(productId, type) {
    // 해당 type에 맞는 게시판 데이터 요청
     $('#review').html('<p>리뷰 게시판을 불러오는 중...</p>');
     $('#qna').html('<p>Q&A 게시판을 불러오는 중...</p>');
    
    $.ajax({
        url: '/product/list/' + productId + '/' + type,  // 게시판 데이터 요청
        type: 'GET',
        success: function(data) {
            if (type === 'review') {
                $('#review-board').html(data);  // review 게시판에 데이터 표시
            } else if (type === 'qna') {
                $('#qna-board').html(data);  // qna 게시판에 데이터 표시
            }
        },
        error: function() {
            if (type === 'review') {
                $('#review-board').html('<p>리뷰 데이터를 불러오는 데 실패했습니다.</p>');
            } else if (type === 'qna') {
                $('#qna-board').html('<p>Q&A 데이터를 불러오는 데 실패했습니다.</p>');
            }
        }
    });
}

// 현재 URL에서 productId 추출
function getProductIdFromUrl() {
    var path = window.location.pathname; // 예: /product/detail/15
    var segments = path.split('/'); // '/'로 분리
    return segments[segments.length - 1]; // 마지막 부분이 productId
}

// 페이지 로드 후 자동으로 review와 qna 게시판 데이터 로드
function loadBoards() {
    var productId = getProductIdFromUrl();  // URL에서 productId 추출
    loadBoardData(productId, 'review');  // 리뷰 게시판 데이터 로드
    loadBoardData(productId, 'qna');     // Q&A 게시판 데이터 로드
}

// 페이지가 로드될 때 자동으로 loadBoards 호출
$(document).ready(function() {
    loadBoards();  // 페이지 로드 후 데이터 로드
});