package com.sipvs.zadanie1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sipvs.zadanie1.models.Form;
import com.sipvs.zadanie1.xml.XMLGenerator;
import com.sipvs.zadanie1.xml.XSDValidator;

@Controller
public class OrderController {

    // View
    @GetMapping("/")
    public String home(Model model) {
        System.out.println("Viewed");
        Form form = new Form();
        model.addAttribute("form", form);
        return "index";
    }

    // Generate XML
    @PostMapping("/generate")
    public String generate(@ModelAttribute Form form, Model model) {
        System.out.println("Generated");
        model.addAttribute("form", form);
        System.out.println(form.getContent());

        // Generate XML
        XMLGenerator.generate(form);

        return "index";
    }

    // Validate XML
    @PostMapping("/validate")
    public String validate(@ModelAttribute Form form, Model model) {
        System.out.println("Validated");
        model.addAttribute("form", form);
        System.out.println(form.getContent());

        XSDValidator.validateXMLSchema();

        return "index";
    }

    // Generate HTML
    @PostMapping("/html")
    public String html(@ModelAttribute Form form, Model model) {
        System.out.println("HTML");
        model.addAttribute("form", form);
        System.out.println(form.getContent());
        return "index";
    }

}
