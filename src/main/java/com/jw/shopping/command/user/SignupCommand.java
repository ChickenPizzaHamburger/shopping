package com.jw.shopping.command.user;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.UserDAO;
import com.jw.shopping.dto.User;
import com.jw.shopping.dto.User.Sex;
import com.jw.shopping.util.Command;

@Service("signupCommand")
public class SignupCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public SignupCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void execute(Model model) {
	    Map<String, Object> map = model.asMap();
	    HttpServletRequest request = (HttpServletRequest) map.get("request");
	    
	    try {
	        request.setCharacterEncoding("UTF-8");
	    } catch (Exception e) {
	        System.out.println("인코딩 설정 오류: " + e.getMessage());
	    }

	    String id = request.getParameter("id");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");
	    String name = request.getParameter("name");
	    String sex = request.getParameter("sex");
	    String birthday = request.getParameter("birthday");
	    
	    // 에러 메시지를 저장할 변수
	    String errorId = null;
	    String errorPwd = null;
	    String errorEmail = null;
	    String errorName = null;

	    // 유효성 검사
	    if (!isValidId(id)) {
	        errorId = "아이디는 4~12자의 영어 또는 영어+숫자 조합으로만 입력 가능하며, 숫자로 시작할 수 없습니다!";
	    }
	    if (!isValidPassword(password, id, birthday)) {
	        errorPwd = "비밀번호는 8자 이상의 영어, 숫자, 특수문자를 조합해야 하며, 아이디나 생년월일과 동일할 수 없습니다!";
	    }
	    if (!isValidEmail(email)) {
	        errorEmail = "이메일 형식을 올바르게 입력하세요! 예: example@domain.com";
	    }
	    
	    if (!isValidName(name)) {
	        errorName = "이름은 한글로 2자 이상 입력해야 합니다!";
	    }

	    UserDAO dao = sqlSession.getMapper(UserDAO.class);
	    
	    // DAO를 이용한 중복 확인
	    if (dao.isUserIdDuplicate(id)) {
	        errorId = "이미 존재하는 아이디입니다.";
	    }
	    if (dao.isEmailDuplicate(email)) {
	        errorEmail = "이미 존재하는 이메일입니다.";
	    }

	    // 에러가 있으면 모델에 추가하고 종료
	    if (errorId != null || errorPwd != null || errorEmail != null || errorName != null) {
	        model.addAttribute("errorId", errorId);
	        model.addAttribute("errorPwd", errorPwd);
	        model.addAttribute("errorEmail", errorEmail);
	        model.addAttribute("errorName", errorName);
	        return;
	    }

	    // User 객체 생성 및 데이터 설정
	    User user = new User();
	    user.setId(id);
	    

	    // 비밀번호가 null이 아니면 암호화하여 설정
	    if (password != null) {
	        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
	    } else {
	        user.setPassword(null);  // 비밀번호가 null일 경우
	    }

	    user.setEmail(email);
	    user.setName(name);

	    try {
	        // 성별을 문자열로 받아서, 해당 값을 Enum으로 변환
	        Sex sexEnum = User.Sex.fromString(sex);  // "MALE" 또는 "FEMALE"
	        user.setSex(sexEnum); // Sex enum 객체 저장
	    } catch (IllegalArgumentException e) {
	        model.addAttribute("error", "성별을 올바르게 선택해주세요.");
	        return;
	    }

	    // LocalDate로 변환하기
	    try {
	        user.setBirthday(LocalDate.parse(birthday));  // String을 LocalDate로 변환
	    } catch (Exception e) {
	        model.addAttribute("error", "생년월일 형식이 올바르지 않습니다.");
	        return;
	    }

	    user.setRole(User.Role.USER); // 기본 역할을 USER로 설정

	    // 회원가입 처리
	    boolean isRegistered = dao.signup(user);
	    if (!isRegistered) {
	        model.addAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
	    }
	}

    // 유효성 검사 메소드들
    private boolean isValidId(String userId) {
        return userId != null && userId.matches("^[a-zA-Z][a-zA-Z0-9]{3,11}$");
    }

    private boolean isValidPassword(String password, String userId, String birthDate) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return password != null && password.matches(passwordRegex) && 
               !password.equals(userId) && 
               (birthDate == null || !password.equals(birthDate));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    private boolean isValidName(String username) {
        return username != null && username.matches("^[가-힣]{2,}$");
    }
}