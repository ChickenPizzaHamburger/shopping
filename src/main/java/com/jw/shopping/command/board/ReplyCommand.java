package com.jw.shopping.command.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.BoardDAO;
import com.jw.shopping.dto.Board;
import com.jw.shopping.dto.Board.BoardType;
import com.jw.shopping.util.Command;

@Service("replyCommand")
public class ReplyCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public ReplyCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		int bGroup = Integer.parseInt(request.getParameter("bGroup"));
		int bStep = Integer.parseInt(request.getParameter("bStep"));
		int bIndent = Integer.parseInt(request.getParameter("bIndent"));
		int productId = Integer.parseInt(request.getParameter("productId"));
        String boardTypeStr = request.getParameter("boardType");
        BoardType boardType = BoardType.valueOf(boardTypeStr.toUpperCase());
		
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		
		// Board 객체 생성
        Board board = new Board();
        board.setbName(bName);
        board.setbTitle(bTitle);
        board.setbContent(bContent);
        board.setbGroup(bGroup);
        board.setbStep(bStep);
        board.setbIndent(bIndent);
        board.setProductId(productId);
        board.setBoardType(boardType);
		
		// 기존 답글의 위치를 조정
        dao.replyShape(bGroup, bStep);
        
        // 새 답글 삽입
		dao.reply(board);
	}

}
