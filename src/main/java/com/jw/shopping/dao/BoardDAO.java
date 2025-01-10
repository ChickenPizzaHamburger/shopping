package com.jw.shopping.dao;

import java.util.ArrayList;
import java.util.List;

import com.jw.shopping.dto.Board;
import com.jw.shopping.dto.Board.BoardType;

public interface BoardDAO {
	
	public ArrayList<Board> list(final int productId, final BoardType type);
	public int write(final Board board);
	public int lastNum();
	public Board contentView(final int bId);
	void upHit(final int bId); // private (→ 같은 패키지 안에서만 사용)
	public void modify(final int bId, final String bName, final String bTitle, final String bContent);
	public void delete(final int bId);
	public Board reply_view(final int bId);
	public void reply(final Board board);
	void replyShape(final int strGroup, final int strStep); // private (→ 같은 패키지 안에서만 사용)
	public List<Board> getBoardListByProductIdAndType(final int productId, final String boardType);
	
}
