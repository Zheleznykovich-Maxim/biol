package com.example.biol.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductControler {
    @GetMapping("/")
    public String products() {
        return "products";
    }
}
