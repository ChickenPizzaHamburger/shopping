	function validateForm(){
		const boardTitle = document.getElementById("bTitle").value;
		const boardContent = document.getElementById("bContent").value;
		
		if(boardName.length < 1) {
			alert("제목을 입력하세요.");
			return false;
			}
		
		if(boardContent.length < 1) {
			alert("내용을 입력하세요.");
			return false;
			}
		
		return true;
		}
	
	
	// 글쓰기 버튼 이벤트 핸들러
	$(document).on('click', 'a[href="../write"]', function (event) {
	    event.preventDefault(); // 기본 동작 방지
	    $.ajax({
	        url: '/product/write', // 요청 URL
	        method: 'GET',        // 요청 메서드
	        success: function (response) {
	            $('#main-content').html(response); // 응답을 #main-content에 삽입
	            console.log("글쓰기 페이지 로드 완료");
	        },
	        error: function (xhr, status, error) {
	            console.error("Error: " + xhr.status + " - " + error);
	            alert("페이지를 로드할 수 없습니다.");
	        }
	    });
	});
	

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