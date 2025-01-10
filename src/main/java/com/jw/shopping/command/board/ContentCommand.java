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

@Service("contentCommand")
public class ContentCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public ContentCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int bId = Integer.parseInt(request.getParameter("bId"));
		
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		
		// 조회수 증가
        dao.upHit(bId);
        
        // 게시글 조회
		Board dto = dao.contentView(bId);
		
		model.addAttribute("content", dto);
	}

}