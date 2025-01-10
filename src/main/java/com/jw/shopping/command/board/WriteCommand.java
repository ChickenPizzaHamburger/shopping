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

@Service("writeCommand")
public class WriteCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public WriteCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        
        String bName = request.getParameter("bName");
        String bTitle = request.getParameter("bTitle");
        String bContent = request.getParameter("bContent");
        int productId = Integer.parseInt(request.getParameter("productId"));
        String boardTypeStr = request.getParameter("boardType");
        BoardType boardType = BoardType.valueOf(boardTypeStr.toUpperCase());

        // 새로운 bGroup 값 계산 (마지막 bId 가져오기)
        BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
        int bGroup = dao.lastNum() + 1; // 새로운 bGroup 값 계산 (lastNum으로 bId 가져오기)
        
        // Board 객체 생성
        Board board = new Board();
        board.setbName(bName);
        board.setbTitle(bTitle);
        board.setbContent(bContent);
        board.setbGroup(bGroup);
        board.setProductId(productId);
        board.setBoardType(boardType);

        // 새 글 작성 (bGroup 값을 지정)
        int ns = dao.write(board);

        if (ns > 0) {
            System.out.println("글쓰기 성공! bGroup: " + bGroup);
        } else {
            System.out.println("글쓰기 실패.");
        }
    }
}
