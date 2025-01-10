package com.jw.shopping.util;

import org.springframework.ui.Model;

public interface Command {
	void execute(Model model);
	default void execute(Model model, String password) {}
}
