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
		// �𵨿��� productId�� �����ɴϴ�.
        int productId = (Integer) model.asMap().get("productId");

        // MyBatis�� ���� DAO ȣ��
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);

        // ��ǰ ����
        boolean isDeleted = dao.deleteProduct(productId);

        // ����� ���� �޽��� �߰�
        if (isDeleted) {
            model.addAttribute("message", "��ǰ�� ���������� �����Ǿ����ϴ�.");
        } else {
            model.addAttribute("error", "��ǰ ������ �����߽��ϴ�.");
        }
	}

}
