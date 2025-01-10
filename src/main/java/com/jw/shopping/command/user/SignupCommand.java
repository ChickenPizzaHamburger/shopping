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
	        System.out.println("���ڵ� ���� ����: " + e.getMessage());
	    }

	    String id = request.getParameter("id");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");
	    String name = request.getParameter("name");
	    String sex = request.getParameter("sex");
	    String birthday = request.getParameter("birthday");
	    
	    // ���� �޽����� ������ ����
	    String errorId = null;
	    String errorPwd = null;
	    String errorEmail = null;
	    String errorName = null;

	    // ��ȿ�� �˻�
	    if (!isValidId(id)) {
	        errorId = "���̵�� 4~12���� ���� �Ǵ� ����+���� �������θ� �Է� �����ϸ�, ���ڷ� ������ �� �����ϴ�!";
	    }
	    if (!isValidPassword(password, id, birthday)) {
	        errorPwd = "��й�ȣ�� 8�� �̻��� ����, ����, Ư�����ڸ� �����ؾ� �ϸ�, ���̵� ������ϰ� ������ �� �����ϴ�!";
	    }
	    if (!isValidEmail(email)) {
	        errorEmail = "�̸��� ������ �ùٸ��� �Է��ϼ���! ��: example@domain.com";
	    }
	    
	    if (!isValidName(name)) {
	        errorName = "�̸��� �ѱ۷� 2�� �̻� �Է��ؾ� �մϴ�!";
	    }

	    UserDAO dao = sqlSession.getMapper(UserDAO.class);
	    
	    // DAO�� �̿��� �ߺ� Ȯ��
	    if (dao.isUserIdDuplicate(id)) {
	        errorId = "�̹� �����ϴ� ���̵��Դϴ�.";
	    }
	    if (dao.isEmailDuplicate(email)) {
	        errorEmail = "�̹� �����ϴ� �̸����Դϴ�.";
	    }

	    // ������ ������ �𵨿� �߰��ϰ� ����
	    if (errorId != null || errorPwd != null || errorEmail != null || errorName != null) {
	        model.addAttribute("errorId", errorId);
	        model.addAttribute("errorPwd", errorPwd);
	        model.addAttribute("errorEmail", errorEmail);
	        model.addAttribute("errorName", errorName);
	        return;
	    }

	    // User ��ü ���� �� ������ ����
	    User user = new User();
	    user.setId(id);
	    

	    // ��й�ȣ�� null�� �ƴϸ� ��ȣȭ�Ͽ� ����
	    if (password != null) {
	        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
	    } else {
	        user.setPassword(null);  // ��й�ȣ�� null�� ���
	    }

	    user.setEmail(email);
	    user.setName(name);

	    try {
	        // ������ ���ڿ��� �޾Ƽ�, �ش� ���� Enum���� ��ȯ
	        Sex sexEnum = User.Sex.fromString(sex);  // "MALE" �Ǵ� "FEMALE"
	        user.setSex(sexEnum); // Sex enum ��ü ����
	    } catch (IllegalArgumentException e) {
	        model.addAttribute("error", "������ �ùٸ��� �������ּ���.");
	        return;
	    }

	    // LocalDate�� ��ȯ�ϱ�
	    try {
	        user.setBirthday(LocalDate.parse(birthday));  // String�� LocalDate�� ��ȯ
	    } catch (Exception e) {
	        model.addAttribute("error", "������� ������ �ùٸ��� �ʽ��ϴ�.");
	        return;
	    }

	    user.setRole(User.Role.USER); // �⺻ ������ USER�� ����

	    // ȸ������ ó��
	    boolean isRegistered = dao.signup(user);
	    if (!isRegistered) {
	        model.addAttribute("error", "ȸ�����Կ� �����߽��ϴ�. �ٽ� �õ����ּ���.");
	    }
	}

    // ��ȿ�� �˻� �޼ҵ��
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
        return username != null && username.matches("^[��-�R]{2,}$");
    }
}