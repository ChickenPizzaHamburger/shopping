package com.jw.shopping.command.product;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.ProductDAO;
import com.jw.shopping.dto.Product;
import com.jw.shopping.util.Command;

@Service("updateProductCommand")
public class UpdateProductCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public UpdateProductCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void execute(Model model) {
		// 모델에서 product 정보를 가져옵니다.
        Product product = (Product) model.asMap().get("product");

        // MyBatis를 통한 DAO 호출
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);

        // 상품 이미지 경로 수정
        boolean isUpdated = dao.updateImagePath(product);

        // 결과에 따라 메시지 추가
        if (isUpdated) {
            model.addAttribute("message", "상품 이미지 경로가 성공적으로 업데이트되었습니다.");
        } else {
            model.addAttribute("error", "상품 이미지 경로 업데이트에 실패했습니다.");
        }

	}

}
