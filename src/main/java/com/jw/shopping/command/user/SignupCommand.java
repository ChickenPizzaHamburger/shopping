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
	        System.out.println("ÀÎÄÚµù ¼³Á¤ ¿À·ù: " + e.getMessage());
	    }

	    String id = request.getParameter("id");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");
	    String name = request.getParameter("name");
	    String sex = request.getParameter("sex");
	    String birthday = request.getParameter("birthday");
	    
	    // ¿¡·¯ ¸Þ½ÃÁö¸¦ ÀúÀåÇÒ º¯¼ö
	    String errorId = null;
	    String errorPwd = null;
	    String errorEmail = null;
	    String errorName = null;

	    // À¯È¿¼º °Ë»ç
	    if (!isValidId(id)) {
	        errorId = "¾ÆÀÌµð´Â 4~12ÀÚÀÇ ¿µ¾î ¶Ç´Â ¿µ¾î+¼ýÀÚ Á¶ÇÕÀ¸·Î¸¸ ÀÔ·Â °¡´ÉÇÏ¸ç, ¼ýÀÚ·Î ½ÃÀÛÇÒ ¼ö ¾ø½À´Ï´Ù!";
	    }
	    if (!isValidPassword(password, id, birthday)) {
	        errorPwd = "ºñ¹Ð¹øÈ£´Â 8ÀÚ ÀÌ»óÀÇ ¿µ¾î, ¼ýÀÚ, Æ¯¼ö¹®ÀÚ¸¦ Á¶ÇÕÇØ¾ß ÇÏ¸ç, ¾ÆÀÌµð³ª »ý³â¿ùÀÏ°ú µ¿ÀÏÇÒ ¼ö ¾ø½À´Ï´Ù!";
	    }
	    if (!isValidEmail(email)) {
	        errorEmail = "ÀÌ¸ÞÀÏ Çü½ÄÀ» ¿Ã¹Ù¸£°Ô ÀÔ·ÂÇÏ¼¼¿ä! ¿¹: example@domain.com";
	    }
	    
	    if (!isValidName(name)) {
	        errorName = "ÀÌ¸§Àº ÇÑ±Û·Î 2ÀÚ ÀÌ»ó ÀÔ·ÂÇØ¾ß ÇÕ´Ï´Ù!";
	    }

	    UserDAO dao = sqlSession.getMapper(UserDAO.class);
	    
	    // DAO¸¦ ÀÌ¿ëÇÑ Áßº¹ È®ÀÎ
	    if (dao.isUserIdDuplicate(id)) {
	        errorId = "ÀÌ¹Ì Á¸ÀçÇÏ´Â ¾ÆÀÌµðÀÔ´Ï´Ù.";
	    }
	    if (dao.isEmailDuplicate(email)) {
	        errorEmail = "ÀÌ¹Ì Á¸ÀçÇÏ´Â ÀÌ¸ÞÀÏÀÔ´Ï´Ù.";
	    }

	    // ¿¡·¯°¡ ÀÖÀ¸¸é ¸ðµ¨¿¡ Ãß°¡ÇÏ°í Á¾·á
	    if (errorId != null || errorPwd != null || errorEmail != null || errorName != null) {
	        model.addAttribute("errorId", errorId);
	        model.addAttribute("errorPwd", errorPwd);
	        model.addAttribute("errorEmail", errorEmail);
	        model.addAttribute("errorName", errorName);
	        return;
	    }

	    // User °´Ã¼ »ý¼º ¹× µ¥ÀÌÅÍ ¼³Á¤
	    User user = new User();
	    user.setId(id);
	    

	    // ºñ¹Ð¹øÈ£°¡ nullÀÌ ¾Æ´Ï¸é ¾ÏÈ£È­ÇÏ¿© ¼³Á¤
	    if (password != null) {
	        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
	    } else {
	        user.setPassword(null);  // ºñ¹Ð¹øÈ£°¡ nullÀÏ °æ¿ì
	    }

	    user.setEmail(email);
	    user.setName(name);

	    try {
	        // ¼ºº°À» ¹®ÀÚ¿­·Î ¹Þ¾Æ¼­, ÇØ´ç °ªÀ» EnumÀ¸·Î º¯È¯
	        Sex sexEnum = User.Sex.fromString(sex);  // "MALE" ¶Ç´Â "FEMALE"
	        user.setSex(sexEnum); // Sex enum °´Ã¼ ÀúÀå
	    } catch (IllegalArgumentException e) {
	        model.addAttribute("error", "¼ºº°À» ¿Ã¹Ù¸£°Ô ¼±ÅÃÇØÁÖ¼¼¿ä.");
	        return;
	    }

	    // LocalDate·Î º¯È¯ÇÏ±â
	    try {
	        user.setBirthday(LocalDate.parse(birthday));  // StringÀ» LocalDate·Î º¯È¯
	    } catch (Exception e) {
	        model.addAttribute("error", "»ý³â¿ùÀÏ Çü½ÄÀÌ ¿Ã¹Ù¸£Áö ¾Ê½À´Ï´Ù.");
	        return;
	    }

	    user.setRole(User.Role.USER); // ±âº» ¿ªÇÒÀ» USER·Î ¼³Á¤

	    // È¸¿ø°¡ÀÔ Ã³¸®
	    boolean isRegistered = dao.signup(user);
	    if (!isRegistered) {
	        model.addAttribute("error", "È¸¿ø°¡ÀÔ¿¡ ½ÇÆÐÇß½À´Ï´Ù. ´Ù½Ã ½ÃµµÇØÁÖ¼¼¿ä.");
	    }
	}

    // À¯È¿¼º °Ë»ç ¸Þ¼Òµåµé
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
        return username != null && username.matches("^[°¡-ÆR]{2,}$");
    }
}