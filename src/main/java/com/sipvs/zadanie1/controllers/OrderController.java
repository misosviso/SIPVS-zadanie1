package com.sipvs.zadanie1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sipvs.zadanie1.models.Form;

@Controller
public class OrderController {

    // Zobrazenie stranky (index.html)
    @GetMapping("/")
	public String home(Model model) {
        System.out.println("Viewed");
        Form form = new Form();
        model.addAttribute("form", form);
		return "index";
	}

    // Reaguje na submit
    @PostMapping("/")
    public String submit(@ModelAttribute Form form, Model model) {
        System.out.println("Generating XML");
        model.addAttribute("form", form);
        return "index";
    }

    // Reaguje na validate
    @PostMapping("/validate")
    public void validate() {
        System.out.println("Validation");
    }

    // Reaguje na html
    @PostMapping("/html")
    public void html() {
        System.out.println("Genrating HTML");
    }

}
