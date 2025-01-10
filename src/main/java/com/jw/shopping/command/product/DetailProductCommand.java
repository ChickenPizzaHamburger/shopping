package com.jw.shopping.command.product;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.ProductDAO;
import com.jw.shopping.dto.Product;
import com.jw.shopping.util.Command;

@Service("detailProductCommand")
public class DetailProductCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public DetailProductCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void execute(Model model) {
        // 모델에서 productId를 가져옵니다.
        int productId = (Integer) model.asMap().get("productId");

        // DAO를 사용해 상품 상세 정보를 가져옵니다.
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);
        Product product = dao.getProductById(productId);
    
        // 상품이 존재하면 모델에 추가
        if (product != null) {
        	product.setImagePath("/img/" + product.getImagePath());	
            model.addAttribute("product", product);
        } else {
            // 상품이 없다면 오류 메시지 추가
            model.addAttribute("error", "상품을 찾을 수 없습니다.");
        }
    }
}