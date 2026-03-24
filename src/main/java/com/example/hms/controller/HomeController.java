package com.example.hms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private static final List<Map<String, String>> TEAM = List.of(
            Map.of("id", "Ayan", "name", "Ayan Pal", "initials", "AP", "num", "01"),
            Map.of("id", "Anubhob",   "name", "Anubhab Dey",     "initials", "AD", "num", "02"),
            Map.of("id", "Arunima", "name", "Arunima Bhattacharyya",   "initials", "AB", "num", "03"),
            Map.of("id", "Bidwattam", "name", "Bidwattam Datta",     "initials", "BD", "num", "04"),
            Map.of("id", "Soumadwip",   "name", "Soumadwip Ghara",  "initials", "SG", "num", "05")
    );

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("team", TEAM);
        return "home";
    }

    @GetMapping("/team/{id}")
    public String profile(@PathVariable String id, Model model) {
        model.addAttribute("team", TEAM);
        return "home";
    }
}
