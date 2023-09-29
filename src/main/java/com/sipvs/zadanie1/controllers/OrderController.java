package com.sipvs.zadanie1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(value = "/", method = RequestMethod.POST, params = "GenerateXML")
    public String submit(@ModelAttribute Form form, Model model) {
        System.out.println("Generating XML");
        model.addAttribute("form", form);
        return "index";
    }

    // Reaguje na validate
    @RequestMapping(value = "/", method = RequestMethod.POST, params = "ValidateXML")
    public String validate(@ModelAttribute Form form, Model model) {
        model.addAttribute("form", form);
        System.out.println("Validation");
        return "index";
    }

    // Reaguje na html
    @RequestMapping(value = "/", method = RequestMethod.POST, params = "GenerateHTML")
    public void html(@ModelAttribute Form form, Model model) {
        model.addAttribute("form", form);
        System.out.println("Generating HTML");
    }

}
