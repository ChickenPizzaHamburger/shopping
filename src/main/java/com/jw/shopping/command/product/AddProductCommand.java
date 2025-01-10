package com.jw.shopping.command.product;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.jw.shopping.dao.ProductDAO;
import com.jw.shopping.dto.Product;
import com.jw.shopping.dto.User;
import com.jw.shopping.util.Command;

@Service("addProductCommand")
public class AddProductCommand implements Command {

	private final SqlSession sqlSession;

    @Autowired
    public AddProductCommand(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void execute(Model model) {
        HttpSession session = (HttpSession) model.asMap().get("session");
        String productName = (String) model.asMap().get("productName");
        BigDecimal productPrice = (BigDecimal) model.asMap().get("productPrice");
        int productNumber = (Integer) model.asMap().get("productNumber");
        int productCategory = (Integer) model.asMap().get("productCategory");
        MultipartFile productImage = (MultipartFile) model.asMap().get("productImage");

        User loggedUser = (User) session.getAttribute("loggedUser");

        // ���� �˻�
        if (loggedUser == null || loggedUser.getRole().getValue() > 100) {
            model.addAttribute("error", "������ �����ϴ�. �����ڸ� ���� �����մϴ�.");
            return;
        }

        // ��ȿ�� �˻�
        if (productName.length() < 5 || productPrice.compareTo(BigDecimal.TEN) < 0 || productNumber < 1) {
            model.addAttribute("error", "�Է°��� ��ȿ���� �ʽ��ϴ�.");
            return;
        }

        // Product ����
        Product product = new Product();
        product.setName(productName);
        product.setPrice(productPrice);
        product.setNumber(productNumber);
        product.setCategory(productCategory);

        // �����ͺ��̽��� ���� �� product_id �������� (dao ȣ��)
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);
        boolean isAdded = dao.addProduct(product, loggedUser.getId());
        if (!isAdded) {
            model.addAttribute("error", "��ǰ ��Ͽ� �����߽��ϴ�.");
            return;
        }

        // ���� product.getId()�� ��ǰ ID�� �����Ǿ� �����Ƿ�, �ش� ID�� ���� ���� ó��
        String uploadDir = "D:" + File.separator + "SpringExam" + File.separator + "Shopping" 
        + File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
        		+ File.separator + "product";
        String dateFolder = new SimpleDateFormat("yyMMdd").format(new Date());
        File folder = new File(uploadDir + File.separator + dateFolder);
        
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // ���� ���� ó��
        String fileName = product.getId() + ".png"; // product_id�� �������� ���� �̸� ����
        String imagePath = dateFolder + "/" + fileName; // �̹��� ���

        try {
            File file = new File(folder, fileName);
            productImage.transferTo(file);
        } catch (IOException e) {
            model.addAttribute("error", "�̹��� ���ε忡 �����߽��ϴ�.");
            return;
        }

        // �̹��� ��θ� Product ��ü�� ����
        product.setImagePath(imagePath);

        // �̹��� ��θ� �����ͺ��̽��� ������Ʈ
        boolean isImagePathUpdated = dao.updateImagePath(product);  // �̹��� ��� ������Ʈ �޼ҵ� �߰� �ʿ�
        if (!isImagePathUpdated) {
            model.addAttribute("error", "�̹��� ��� ���忡 �����߽��ϴ�.");
            return;
        }

        model.addAttribute("message", "��ǰ�� ���������� ��ϵǾ����ϴ�!");
    }
}
