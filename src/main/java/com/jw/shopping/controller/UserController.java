package com.jw.shopping.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jw.shopping.util.Command;

@Controller
public class UserController {

	@Inject
    private Map<String, Command> commands;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String login(@RequestParam("id") String id, @RequestParam("password") String password, HttpServletRequest request, Model model) {
	    // ��й�ȣ�� ������� ��� ���� �޽��� ó��
	    if (password == null || password.isEmpty()) {
	        model.addAttribute("error", "Password cannot be empty");
	        return "login";  // �α��� �������� ���ư��鼭 ���� �޽��� ǥ��
	    }

	    // ���ǿ��� id ���� �����ϰ� �α��� ó���� ���� Command ����
	    HttpSession session = request.getSession();
	    session.setAttribute("id", id);
	    model.addAttribute("session", session);
	    
	    // ��й�ȣ�� �ѱ�� �α��� ó�� ��ɾ� ����
	    executeCommand("loginCommand", model, password);  // ��й�ȣ�� ����

	    // �α��� �� �����̷�Ʈ
	    return "redirect:/";  // �α��� �� Ȩ �������� �����̷�Ʈ
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		executeCommand("signupCommand", model);
		return "redirect:/";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
	    session.invalidate(); // �α׾ƿ� �� ���� �ʱ�ȭ
	    return "redirect:/"; // �α׾ƿ� �� ���� �������� �����̷�Ʈ
	}
	
	private void executeCommand(String commandName, Model model) {
		Command command = commands.get(commandName);
		if (command != null) {
			command.execute(model);
		} else {
			throw new IllegalArgumentException("Command not fount: " + commandName);
		}
	}
	
	private void executeCommand(String commandName, Model model, String password) {
	    Command command = commands.get(commandName);
	    if (command != null) {
	        command.execute(model, password);  // ��й�ȣ�� �����Ͽ� execute(Model, String password) ȣ��
	    } else {
	        throw new IllegalArgumentException("Command not found: " + commandName);
	    }
	}
}
