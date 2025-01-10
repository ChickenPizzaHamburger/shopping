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
	    // 비밀번호가 비어있을 경우 오류 메시지 처리
	    if (password == null || password.isEmpty()) {
	        model.addAttribute("error", "Password cannot be empty");
	        return "login";  // 로그인 페이지로 돌아가면서 오류 메시지 표시
	    }

	    // 세션에서 id 값을 저장하고 로그인 처리를 위해 Command 실행
	    HttpSession session = request.getSession();
	    session.setAttribute("id", id);
	    model.addAttribute("session", session);
	    
	    // 비밀번호를 넘기며 로그인 처리 명령어 실행
	    executeCommand("loginCommand", model, password);  // 비밀번호를 전달

	    // 로그인 후 리다이렉트
	    return "redirect:/";  // 로그인 후 홈 페이지로 리다이렉트
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		executeCommand("signupCommand", model);
		return "redirect:/";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
	    session.invalidate(); // 로그아웃 시 세션 초기화
	    return "redirect:/"; // 로그아웃 후 메인 페이지로 리다이렉트
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
	        command.execute(model, password);  // 비밀번호를 전달하여 execute(Model, String password) 호출
	    } else {
	        throw new IllegalArgumentException("Command not found: " + commandName);
	    }
	}
}
