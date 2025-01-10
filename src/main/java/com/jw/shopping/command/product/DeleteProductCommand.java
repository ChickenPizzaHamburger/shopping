package com.jw.shopping.command.product;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.ProductDAO;
import com.jw.shopping.util.Command;

@Service("deleteProductCommand")
public class DeleteProductCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public DeleteProductCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void execute(Model model) {
		// 모델에서 productId를 가져옵니다.
        int productId = (Integer) model.asMap().get("productId");

        // MyBatis를 통한 DAO 호출
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);

        // 상품 삭제
        boolean isDeleted = dao.deleteProduct(productId);

        // 결과에 따라 메시지 추가
        if (isDeleted) {
            model.addAttribute("message", "상품이 성공적으로 삭제되었습니다.");
        } else {
            model.addAttribute("error", "상품 삭제에 실패했습니다.");
        }
	}

}
