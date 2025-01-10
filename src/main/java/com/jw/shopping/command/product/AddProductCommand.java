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

        // 권한 검사
        if (loggedUser == null || loggedUser.getRole().getValue() > 100) {
            model.addAttribute("error", "권한이 없습니다. 관리자만 접근 가능합니다.");
            return;
        }

        // 유효성 검사
        if (productName.length() < 5 || productPrice.compareTo(BigDecimal.TEN) < 0 || productNumber < 1) {
            model.addAttribute("error", "입력값이 유효하지 않습니다.");
            return;
        }

        // Product 생성
        Product product = new Product();
        product.setName(productName);
        product.setPrice(productPrice);
        product.setNumber(productNumber);
        product.setCategory(productCategory);

        // 데이터베이스에 저장 후 product_id 가져오기 (dao 호출)
        ProductDAO dao = sqlSession.getMapper(ProductDAO.class);
        boolean isAdded = dao.addProduct(product, loggedUser.getId());
        if (!isAdded) {
            model.addAttribute("error", "상품 등록에 실패했습니다.");
            return;
        }

        // 이제 product.getId()에 상품 ID가 설정되어 있으므로, 해당 ID로 파일 저장 처리
        String uploadDir = "D:" + File.separator + "SpringExam" + File.separator + "Shopping" 
        + File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
        		+ File.separator + "product";
        String dateFolder = new SimpleDateFormat("yyMMdd").format(new Date());
        File folder = new File(uploadDir + File.separator + dateFolder);
        
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 파일 저장 처리
        String fileName = product.getId() + ".png"; // product_id를 기준으로 파일 이름 설정
        String imagePath = dateFolder + "/" + fileName; // 이미지 경로

        try {
            File file = new File(folder, fileName);
            productImage.transferTo(file);
        } catch (IOException e) {
            model.addAttribute("error", "이미지 업로드에 실패했습니다.");
            return;
        }

        // 이미지 경로를 Product 객체에 설정
        product.setImagePath(imagePath);

        // 이미지 경로를 데이터베이스에 업데이트
        boolean isImagePathUpdated = dao.updateImagePath(product);  // 이미지 경로 업데이트 메소드 추가 필요
        if (!isImagePathUpdated) {
            model.addAttribute("error", "이미지 경로 저장에 실패했습니다.");
            return;
        }

        model.addAttribute("message", "상품이 성공적으로 등록되었습니다!");
    }
}
