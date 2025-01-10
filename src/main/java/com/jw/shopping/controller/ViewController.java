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
	    // ���ǿ� loggedUser�� ������ main ��������, ������ index ��������
	    if (session.getAttribute("loggedUser") != null) {
	        return "shopping/main"; // loggedUser�� ���� �� main ��������
	    } else {
	        return "shopping/index"; // loggedUser�� ���� �� index ��������
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
	        model.addAttribute("error", "������ �����ϴ�. �����ڸ� ���� �����մϴ�.");
	        return "shopping/index"; // ���� ���� �� ���� �������� �����̷�Ʈ
	    }
	    return "shopping/addProduct";
	}
}
