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
		// �𵨿��� product ������ �����ɴϴ�.
        Product product = (Product) model.asMap().get("product");

        // MyBatis�� ���� DAO ȣ��
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);

        // ��ǰ �̹��� ��� ����
        boolean isUpdated = dao.updateImagePath(product);

        // ����� ���� �޽��� �߰�
        if (isUpdated) {
            model.addAttribute("message", "��ǰ �̹��� ��ΰ� ���������� ������Ʈ�Ǿ����ϴ�.");
        } else {
            model.addAttribute("error", "��ǰ �̹��� ��� ������Ʈ�� �����߽��ϴ�.");
        }

	}

}
