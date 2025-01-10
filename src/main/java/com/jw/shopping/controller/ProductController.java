package com.jw.shopping.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jw.shopping.util.Command;

@Controller
public class ProductController {
    
	@Inject
    private Map<String, Command> commands;

	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct(
	        @RequestParam("productName") String productName,
	        @RequestParam("productPrice") BigDecimal productPrice,
	        @RequestParam("productNumber") int productNumber,
	        @RequestParam("productImage") MultipartFile productImage,
	        @RequestParam("productCategory") int productCategory,
	        HttpSession session, Model model) {
		
		System.out.println("image : " + productImage);

	    model.addAttribute("productName", productName);
	    model.addAttribute("productPrice", productPrice);
	    model.addAttribute("productNumber", productNumber);
	    model.addAttribute("productImage", productImage);
	    model.addAttribute("productCategory", productCategory);
	    model.addAttribute("session", session);

	    executeCommand("addProductCommand", model);

	    // 성공 또는 실패 메시지에 따라 리다이렉트 처리
	    if (model.containsAttribute("error")) {
	        return "redirect:/";
	    }
	    return "shopping/main";
	}
	
	@RequestMapping(value = "/product/{category}", method = RequestMethod.GET)
	public String getProductsByCategory(@PathVariable String category, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
	    model.addAttribute("category", category);
	    model.addAttribute("page", page);
	    executeCommand("getProductsCommand", model);
	    return "shopping/product";
	}
	
	@RequestMapping(value = "/product/all", method = RequestMethod.GET)
	public String getProductsAll(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
	    model.addAttribute("category", "all");
	    model.addAttribute("page", page);
	    executeCommand("getProductsCommand", model);
	    return "shopping/product";
	}
	
	@RequestMapping(value = "/product/detail/{id}", method = RequestMethod.GET)
	public String productDetail(@PathVariable int id, Model model) {
		model.addAttribute("productId", id);
		executeCommand("detailProductCommand", model);
		return "shopping/product-detail";
	}
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable int id, Model model) {
        model.addAttribute("productId", id);
        executeCommand("deleteProductCommand", model);
        return "redirect:/";
    }
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public String updateProduct(
            @PathVariable int id,
            @RequestParam("productName") String productName,
            @RequestParam("productPrice") BigDecimal productPrice,
            @RequestParam("productNumber") int productNumber,
            @RequestParam("productCategory") int productCategory,
            HttpSession session, Model model) {

        model.addAttribute("productId", id);
        model.addAttribute("productName", productName);
        model.addAttribute("productPrice", productPrice);
        model.addAttribute("productNumber", productNumber);
        model.addAttribute("productCategory", productCategory);
        model.addAttribute("session", session);

        executeCommand("updateProductCommand", model);
        return "redirect:/";
    }
	
//	@RequestMapping(value = "/jwcorp", method = RequestMethod.GET)
//	public String jwcorp() {
//		return "shopping/jwcorp";
//	}
//	
//	@RequestMapping(value = "/recruit", method = RequestMethod.GET)
//	public String recruit() {
//		return "shopping/recruit";
//	}
//	
//	@RequestMapping(value = "/proposal", method = RequestMethod.GET)
//	public String proposal() {
//		return "shopping/proposal";
//	}
//	
//	@RequestMapping(value = "/service", method = RequestMethod.GET)
//	public String service() {
//		return "shopping/service";
//	}
//	
//	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
//	public String privacy() {
//		return "shopping/privacy";
//	}
//	
//	@RequestMapping(value = "/help", method = RequestMethod.GET)
//	public String help() {
//		return "shopping/help";
//	}
	
	private void executeCommand(String commandName, Model model) {
		Command command = commands.get(commandName);
		if (command != null) {
			command.execute(model);
		} else {
			throw new IllegalArgumentException("Command not fount: " + commandName);
		}
	}
}
