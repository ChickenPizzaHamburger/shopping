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
        // �𵨿��� productId�� �����ɴϴ�.
        int productId = (Integer) model.asMap().get("productId");

        // DAO�� ����� ��ǰ �� ������ �����ɴϴ�.
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);
        Product product = dao.getProductById(productId);
    
        // ��ǰ�� �����ϸ� �𵨿� �߰�
        if (product != null) {
        	product.setImagePath("/img/" + product.getImagePath());	
            model.addAttribute("product", product);
        } else {
            // ��ǰ�� ���ٸ� ���� �޽��� �߰�
            model.addAttribute("error", "��ǰ�� ã�� �� �����ϴ�.");
        }
    }
}