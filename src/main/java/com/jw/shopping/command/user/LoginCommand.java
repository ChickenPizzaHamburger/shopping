package com.jw.shopping.command.user;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.UserDAO;
import com.jw.shopping.dto.User;
import com.jw.shopping.util.Command;

@Service("loginCommand")
public class LoginCommand implements Command {

    private final SqlSession sqlSession;

    @Autowired
    public LoginCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void execute(Model model) {}
    
    @Override
    public void execute(Model model, String password) {
        HttpSession session = (HttpSession) model.asMap().get("session");
        String id = (String) session.getAttribute("id");

        // 로그인 처리 로직
        UserDAO dao = sqlSession.getMapper(UserDAO.class);
        
        // 비밀번호 조회
        String storedPassword = dao.getPasswordByUserId(id);
        
        // 비밀번호 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean check = false;
        
        if (storedPassword != null) {
            check = encoder.matches(password, storedPassword);  // 비밀번호 일치 확인
        }

        if (check) {
            // 로그인 성공 시 세션에 User 객체 저장
            User user = dao.getUserById(id);
            session.setAttribute("loggedUser", user);
        } else {
            // 로그인 실패 시 오류 메시지 처리
            model.addAttribute("error", "Invalid username or password.");
        }
    }
}