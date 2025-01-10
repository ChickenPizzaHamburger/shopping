        const ID = document.getElementById("id");
        const PW = document.getElementById("password");
        const PWChecked = document.getElementById("pwdChecked");
        const Email = document.getElementById("email");
        const Name = document.getElementById("name");
        const birthDate = document.getElementById("birthday");
        
        // 에러 메시지 표시 함수
        function displayError(elementId, message) {
		    const errorElement = document.getElementById(elementId);
		    if (errorElement) {
		        errorElement.textContent = message;
		    }
		}
        
        // 에러 메시지 제거 함수
        function clearError(elementId) {
		    const errorElement = document.getElementById(elementId);
		    if (errorElement) {
		        errorElement.textContent = '';  // 메시지를 빈 문자열로 설정하여 제거
		    }
		}
        
        // 유효성 검사 함수들
        function validId(){
        	const idRegExp = /^[a-zA-Z][0-9a-zA-Z]{3, 11}$/;
        	if (!idRegExp.test(ID.value)){
        		displayError("userIdError", "아이디는 4~12자의 영어 또는 영어+숫자 조합으로만 입력 가능하며, 숫자로 시작할 수 없습니다!");
        		ID.classList.add("is-invalid");
        		return false;
        	}
        	clearError("userIdError");
        	ID.classList.remove("is-invalid");
  			return true;
        }
        
        function signup() {
		    var sex = document.querySelector('input[name="sex"]:checked');
		    if (!sex) {
		        alert("성별을 선택해주세요.");
		        return false;
		    }
		    return true;
		}
        
        function validPW() {
			  const pwRegExp = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[``~!@#$%^&*().]).{8,}$/;
			  
			  if (!pwRegExp.test(PW.value)) {
			    displayError("userPwdError", "비밀번호는 8자 이상의 영어, 숫자, 특수문자를 조합해야 합니다!");
			    PW.classList.add("is-invalid");
			    return false;
			  }
			  
			  if (PW.value !== PWChecked.value) {
			    displayError("userPwdError", "비밀번호가 다릅니다.");
			    PW.classList.add("is-invalid");
			    return false; 
			  }
			
			  if (PW.value === ID.value) {
			    displayError("userPwdError", "비밀번호는 아이디와 동일할 수 없습니다!");
			    PW.classList.add("is-invalid");
			    return false;
			  }
			
			  if (PW.value === birthDate.value.replace(/-/g, "")) {
			    displayError("userPwdError", "비밀번호는 생년월일과 동일할 수 없습니다!");
			    PW.classList.add("is-invalid");
			    return false;
			  }
			
			  clearError("userPwdError");
			  PW.classList.remove("is-invalid");
			  return true;
			}
		
		function validEmail() {
		  const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,4}$/;
		  if (!emailRegExp.test(Email.value)) {
		    displayError("userEmailError", "이메일 형식을 올바르게 입력하세요! 예: example@domain.com");
		    Email.classList.add("is-invalid");
		    return false;
		  }
		  clearError("userEmailError");
		  Email.classList.remove("is-invalid");
		  return true;
		}
		
		function validName() {
		  const nameRegExp = /^[가-힣]{2,}$/;
		  if (!nameRegExp.test(Name.value)) {
		    displayError("userNameError", "이름은 한글로 2자 이상 입력해야 합니다!");
		    Name.classList.add("is-invalid");
		    return false;
		  }
		  clearError("userNameError");
		  Name.classList.remove("is-invalid");
		  return true;
		}
		
		function validBirthday() {
		  const today = new Date();
		  const birthDateValue = new Date(birthDate.value);
		
		  // 생년월일이 120년 이상 과거인지 확인
		  const minDate = new Date();
		  minDate.setFullYear(today.getFullYear() - 120);  // 120년 전 날짜
		
		  // 생년월일이 현재 날짜보다 미래인지 확인
		  const maxDate = new Date();
		  maxDate.setFullYear(today.getFullYear() - 10);  // 10년 전 날짜 (최대 연령 10세 제한)
		
		  if (birthDateValue < minDate || birthDateValue > maxDate) {
		    displayError("userBirthdayError", "생년월일이 올바르지 않습니다. 120년 이내, 10년 이상의 연령만 가능합니다.");
		    birthDate.classList.add("is-invalid");
		    return false;
		  }
		
		  clearError("userBirthdayError");
		  birthDate.classList.remove("is-invalid");
		  return true;
		}
		
		// 아이디와 이메일 중복 검사
		function checkDuplication() {
		  if (ID.value === Email.value) {
		    displayError("userEmailError", "아이디와 이메일은 동일할 수 없습니다!");
		    Email.classList.add("is-invalid");
		    return false;
		  }
		  clearError("userEmailError");
		  Email.classList.remove("is-invalid");
		  return true;
		}
		
		// 유효성 검사 후 폼 제출 여부 결정
		function valid() {
		  const isValidId = validId();
		  const isValidPw = validPW();
		  const isValidEmail = validEmail();
		  const isValidName = validName();
		  const isValidDuplication = checkDuplication(); // 아이디와 이메일 중복 확인
		  const isValidBirthday = validBirthday();  // 생년월일 유효성 검사
		
		  return isValidId && isValidPw && isValidEmail && isValidName && isValidDuplication && isValidBirthday;
		}
        
        // 입력 필드에서 blur나 input 이벤트 발생 시 유효성 검사
		ID.addEventListener('blur', validId);
		ID.addEventListener('input', validId);
		
		PW.addEventListener('blur', validPW);
		PW.addEventListener('input', validPW);
		
		Email.addEventListener('blur', validEmail);
		Email.addEventListener('input', validEmail);
		
		Name.addEventListener('blur', validName);
		Name.addEventListener('input', validName);
		
		birthDate.addEventListener('blur', validBirthday);
		birthDate.addEventListener('input', validBirthday);
		
		// 폼 제출 시 유효성 검사
		document.getElementById("signupForm").addEventListener("submit", function(event) {
		  if (!valid()) {
		    event.preventDefault();   // 유효성 검사에 실패하면 폼 제출을 막음
		  }
		});