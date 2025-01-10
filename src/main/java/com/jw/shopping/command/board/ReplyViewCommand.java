package com.jw.shopping.command.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.BoardDAO;
import com.jw.shopping.dto.Board;
import com.jw.shopping.util.Command;

@Service("replyViewCommand")
public class ReplyViewCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public ReplyViewCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int bId = Integer.parseInt(request.getParameter("bId"));
		
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		Board dto = dao.reply_view(bId);
		model.addAttribute("reply", dto);
	}

}
