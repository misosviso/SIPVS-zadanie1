package com.sipvs.zadanie1.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;

import com.sipvs.zadanie1.models.Form;
import com.sipvs.zadanie1.models.Rating;
import com.sipvs.zadanie1.xml.XMLGenerator;
import com.sipvs.zadanie1.xml.XSDValidator;
import com.sipvs.zadanie1.xml.XSLViewer;

@Controller
public class OrderController {

    // View
    @GetMapping("/")
    public String home(Model model) {
        System.out.println("Viewed");
        Form form = new Form();
        form.setRatings(Arrays.asList(new Rating()));
        model.addAttribute("form", form);
        return "index";
    }

    // Generate XML
    @PostMapping("/generate")
    public String generate(@ModelAttribute Form form, Model model) {
        System.out.println("Generated");
        model.addAttribute("form", form);
        System.out.println(form.getContent());

        // set id for each rating
        List<Rating> ratings = form.getRatings();
        for (int i = 0; i < ratings.size(); i++) {
            ratings.get(i).setId(i + 1);
        }
        // Generate XML 
        XMLGenerator.generate(form);

        return "index";
    }

    // Add rating
    @PostMapping("/add")
    public String add(@ModelAttribute Form form, Model model) {
        System.out.println("Added");
        List<Rating> ratings = form.getRatings();
        ratings.add(new Rating());

        model.addAttribute("form", form);

        System.out.println(form.getContent());

        return "index";
    }

    // Validate XML
    @PostMapping("/validate")
    public String validate(@ModelAttribute Form form, Model model) {
        System.out.println("Validated");
        model.addAttribute("form", form);
        System.out.println(form.getContent());

        try {
            XSDValidator.validateXMLSchema();
            model.addAttribute("validationSuccess", "The XML is valid");
            model.addAttribute("validationError", "");
        } catch (SAXException e) {
            model.addAttribute("validationSuccess", "");
            model.addAttribute("validationError", e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception IO: " + e.getMessage());
            model.addAttribute("validationSuccess", "");
            model.addAttribute("validationError", "Error while validating schema " + e.getMessage());
        }

        return "index";
    }

    // Generate HTML
    @PostMapping("/html")
    public String html(@ModelAttribute Form form, Model model) {
        System.out.println("HTML");
        XSLViewer.displayXSL();
        return "index";
    }

}
