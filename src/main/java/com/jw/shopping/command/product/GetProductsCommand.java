package com.jw.shopping.command.product;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jw.shopping.dao.ProductDAO;
import com.jw.shopping.dto.Product;
import com.jw.shopping.util.CategoryMapping;
import com.jw.shopping.util.Command;

@Service("getProductsCommand")
public class GetProductsCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public GetProductsCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void execute(Model model) {
        String category = (String) model.asMap().get("category");
        Integer page = (Integer) model.asMap().get("page");
        if (page == null) {
            page = 1;  // 기본 페이지는 1
        }
        int pageSize = 12;  // 한 페이지에 표시할 상품 개수
        
        // 카테고리 ID를 가져오기
        Integer categoryId = CategoryMapping.getCategoryId(category);
        if (categoryId == null) {
            model.addAttribute("error", "유효하지 않은 카테고리입니다.");
            return;
        }
        
        // MyBatis를 통한 DAO 호출
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);

        // offset 계산 (외부 계산)
        int offset = (page - 1) * pageSize;
        
        // 카테고리별로 상품 목록을 가져오고, 페이지네이션 처리
        List<Product> products = dao.getProducts(categoryId, page, pageSize, offset); // 카테고리와 페이지에 맞는 상품 리스트를 가져옵니다.
        int totalPages = dao.getTotalPages(categoryId, pageSize);  // 카테고리별 전체 페이지 수 계산
        
        for (Product product : products) {
        	product.setImagePath("/img/" + product.getImagePath());
        }

        // 모델에 데이터 추가
        model.addAttribute("products", products);  // 상품 목록
        model.addAttribute("category", category);  // 카테고리 정보
        model.addAttribute("currentPage", page);   // 현재 페이지
        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수
    }
}