package com.jw.shopping.command.board;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.BoardDAO;
import com.jw.shopping.dto.Board;
import com.jw.shopping.util.Command;

@Service("listCommand")
public class ListCommand implements Command {
    
	private final SqlSession sqlSession;

    @Autowired
    public ListCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void execute(Model model) {
        int productId = (Integer) model.asMap().get("productId");
        String productTypeStr = (String) model.asMap().get("productType"); 
        
        // String을 BoardType으로 변환
        Board.BoardType productType = Board.BoardType.valueOf(productTypeStr.toUpperCase());
        
        BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
        ArrayList<Board> list = dao.list(productId, productType);
        model.addAttribute("list", list);
    }
}