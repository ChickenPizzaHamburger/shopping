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

        // �α��� ó�� ����
        UserDAO dao = sqlSession.getMapper(UserDAO.class);
        
        // ��й�ȣ ��ȸ
        String storedPassword = dao.getPasswordByUserId(id);
        
        // ��й�ȣ ��
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean check = false;
        
        if (storedPassword != null) {
            check = encoder.matches(password, storedPassword);  // ��й�ȣ ��ġ Ȯ��
        }

        if (check) {
            // �α��� ���� �� ���ǿ� User ��ü ����
            User user = dao.getUserById(id);
            session.setAttribute("loggedUser", user);
        } else {
            // �α��� ���� �� ���� �޽��� ó��
            model.addAttribute("error", "Invalid username or password.");
        }
    }
}