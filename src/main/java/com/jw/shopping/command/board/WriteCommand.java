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

        // ���ο� bGroup �� ��� (������ bId ��������)
        BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
        int bGroup = dao.lastNum() + 1; // ���ο� bGroup �� ��� (lastNum���� bId ��������)
        
        // Board ��ü ����
        Board board = new Board();
        board.setbName(bName);
        board.setbTitle(bTitle);
        board.setbContent(bContent);
        board.setbGroup(bGroup);
        board.setProductId(productId);
        board.setBoardType(boardType);

        // �� �� �ۼ� (bGroup ���� ����)
        int ns = dao.write(board);

        if (ns > 0) {
            System.out.println("�۾��� ����! bGroup: " + bGroup);
        } else {
            System.out.println("�۾��� ����.");
        }
    }
}
