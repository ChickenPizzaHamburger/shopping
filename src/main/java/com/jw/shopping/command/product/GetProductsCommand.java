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
            page = 1;  // �⺻ �������� 1
        }
        int pageSize = 12;  // �� �������� ǥ���� ��ǰ ����
        
        // ī�װ� ID�� ��������
        Integer categoryId = CategoryMapping.getCategoryId(category);
        if (categoryId == null) {
            model.addAttribute("error", "��ȿ���� ���� ī�װ��Դϴ�.");
            return;
        }
        
        // MyBatis�� ���� DAO ȣ��
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);

        // offset ��� (�ܺ� ���)
        int offset = (page - 1) * pageSize;
        
        // ī�װ����� ��ǰ ����� ��������, ���������̼� ó��
        List<Product> products = dao.getProducts(categoryId, page, pageSize, offset); // ī�װ��� �������� �´� ��ǰ ����Ʈ�� �����ɴϴ�.
        int totalPages = dao.getTotalPages(categoryId, pageSize);  // ī�װ��� ��ü ������ �� ���
        
        for (Product product : products) {
        	product.setImagePath("/img/" + product.getImagePath());
        }

        // �𵨿� ������ �߰�
        model.addAttribute("products", products);  // ��ǰ ���
        model.addAttribute("category", category);  // ī�װ� ����
        model.addAttribute("currentPage", page);   // ���� ������
        model.addAttribute("totalPages", totalPages);  // ��ü ������ ��
    }
}