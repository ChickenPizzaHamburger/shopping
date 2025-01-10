package com.jw.shopping.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jw.shopping.dto.User;
import com.jw.shopping.util.Command;

@Controller
public class BoardController {
	
		// Command 객체들을 Map으로 관리
	    @Inject
	    private Map<String, Command> boardCommands;
		
		@RequestMapping(value = "/product/list/{id}/{type}", method = RequestMethod.GET)
		public String getProductDetail(@PathVariable int id, @PathVariable String type, Model model) {
			model.addAttribute("productId", id);
			model.addAttribute("productType", type);
			executeCommand("listCommand", model);
	        return "board/list"; // product-detail.jsp로 데이터 전달
	    }
		
		@RequestMapping(value = "/product/write", method = RequestMethod.GET)
		public String write(HttpSession session, Model model) {
	        User loggedUser = (User) session.getAttribute("loggedUser");
	        model.addAttribute("loggedUser", loggedUser);
	        return "board/write";
	    }
		
		@RequestMapping(value = "write", method = RequestMethod.POST)
		public String write(HttpServletRequest request, Model model) {
			model.addAttribute("request", request);
			executeCommand("writeCommand", model);
			return "redirect:list";
		}
		
		@RequestMapping(value = "content", method = RequestMethod.GET)
		public String content(HttpServletRequest request, Model model) {
			model.addAttribute("request", request);
			executeCommand("contentCommand", model);
			return "content";
		}
		
		@RequestMapping(value = "reply", method = RequestMethod.GET)
		public String replyView(HttpServletRequest request, Model model) {
			model.addAttribute("request", request);
			executeCommand("replyViewCommand", model);
			return "reply";
		}
		
		@RequestMapping(value = "modify", method = RequestMethod.PUT)
		public String modify(HttpServletRequest request, Model model) {
			model.addAttribute("request", request);
			executeCommand("modifyCommand", model);
			return "redirect:list";
		}
		
		@RequestMapping(value = "delete", method = RequestMethod.DELETE)
		public String delete(HttpServletRequest request, Model model) {
			model.addAttribute("request", request);
			executeCommand("deleteCommand", model);
			return "redirect:list";
			
		}
		
		@RequestMapping(value = "reply", method = RequestMethod.POST)
		public String reply(HttpServletRequest request, Model model) {
			model.addAttribute("request", request);
			executeCommand("replyCommand", model);
			return "redirect:list";
		}
		
		private void executeCommand(String commandName, Model model) {
			Command command = boardCommands.get(commandName);
			if (command != null) {
				command.execute(model);
			} else {
				throw new IllegalArgumentException("Command not fount: " + commandName);
			}
		}
}
