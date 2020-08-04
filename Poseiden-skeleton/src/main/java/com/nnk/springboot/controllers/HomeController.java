package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * These controllers get access to the homepage
 */
@Controller
public class HomeController
{
	@RequestMapping("/")
	public String getHome(Model model)
	{

		return "home";
	}

	@RequestMapping("/admin/home")
	public String getAdminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
