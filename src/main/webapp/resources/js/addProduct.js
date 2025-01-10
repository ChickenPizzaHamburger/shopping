function validateForm() {
    const productName = document.getElementById("productName").value;
    const productPrice = document.getElementById("productPrice").value;
    const productNumber = document.getElementById("productNumber").value;

    if (productName.length < 5) {
        alert("상품 이름은 최소 5자 이상이어야 합니다.");
        return false;
    }

    if (productPrice < 10) {
        alert("상품 가격은 최소 10원 이상이어야 합니다.");
        return false;
    }

    if (productNumber < 1) {
        alert("상품 갯수는 최소 1개 이상이어야 합니다.");
        return false;
    }

    return true;
}

// 상품 추가 폼 제출 시 (POST 요청 처리)
$(document).on('submit', 'form[action="addProduct"]', function(event) {
    event.preventDefault();

    var formData = new FormData(this);  // 폼 데이터를 FormData로 추출

    $.ajax({
        url: '/addProduct',  // 실제 POST 요청 처리 URL
        type: 'POST',
        data: formData,
        processData: false,  // 폼 데이터 자동 처리 방지
        contentType: false,  // 콘텐츠 타입을 지정하지 않음
        success: function(response) {
            // 성공 시 알림 표시
            showAlert(response.message, 'success');
        },
        error: function(xhr, status, error) {
            // 실패 시 알림 표시
            console.error("Error: ", error);
            console.error("Status: ", status);
            console.error("Response: ", xhr.responseText);
            showAlert('상품 등록에 실패했습니다.', 'error');
        }
    });
});

// 알림을 화면 상단에 표시하는 함수
function showAlert(message, type) {
    var alertBox = $('<div>')
        .addClass('alert')
        .addClass(type)  // 'success' 또는 'error' 클래스 추가
        .text(message);

    // 알림을 body에 추가하고 애니메이션을 이용해 나타나게 함
    $('body').prepend(alertBox);

    // 일정 시간 후 알림을 사라지게 함
    setTimeout(function() {
        alertBox.fadeOut(function() {
            $(this).remove();
        });
    }, 3000);  // 3초 후 알림 사라짐
}
