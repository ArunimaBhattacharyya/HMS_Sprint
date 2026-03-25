package com.example.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/sections")
public class SectionController {

    @GetMapping("/{id}")
    public String sectionsPage(@PathVariable String id, Model model) {
        Map<String, Integer> personMap = Map.of(
                "Ayan",      1,
                "Anubhob",   2,
                "Arunima",   3,
                "Bidwattam", 4,
                "Soumadwip", 5
        );
        model.addAttribute("personId", personMap.getOrDefault(id, 1));
        return "section";
    }
}
