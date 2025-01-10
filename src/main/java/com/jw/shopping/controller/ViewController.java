package com.jw.shopping.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jw.shopping.dto.User;

@Controller
public class ViewController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session) {
	    // 세션에 loggedUser가 있으면 main 페이지로, 없으면 index 페이지로
	    if (session.getAttribute("loggedUser") != null) {
	        return "shopping/main"; // loggedUser가 있을 때 main 페이지로
	    } else {
	        return "shopping/index"; // loggedUser가 없을 때 index 페이지로
	    }
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public String signup() {
		return "shopping/signup";
	}
	
	@RequestMapping(value = "addProduct", method = RequestMethod.GET)
	public String addProduct(HttpSession session, Model model) {
	    User loggedUser = (User) session.getAttribute("loggedUser");
	    if (loggedUser == null || loggedUser.getRole().getValue() > 100) {
	        model.addAttribute("error", "권한이 없습니다. 관리자만 접근 가능합니다.");
	        return "shopping/index"; // 권한 없을 시 메인 페이지로 리다이렉트
	    }
	    return "shopping/addProduct";
	}
}
