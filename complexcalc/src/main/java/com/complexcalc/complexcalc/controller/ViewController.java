package com.complexcalc.complexcalc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String showLogin() {
        // Retorna "login" y se espera que exista un archivo login.html en templates/
        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {
        // Retorna "register" y se espera que exista un archivo register.html en templates/
        return "register";
    }

    @GetMapping("/calculator")
    public String showCalculator() {
        // Retorna "calculator" y se espera que exista un archivo calculator.html en templates/
        return "calculator";
    }
}

