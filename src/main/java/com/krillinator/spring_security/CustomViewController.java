package com.krillinator.spring_security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomViewController {

    @GetMapping("/admin")
    public String adminPage() {

        return "adminpage"; // Must Reflect .html document name
    }

}
