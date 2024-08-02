package com.example.demo.controller;

import com.example.demo.service.GoldPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gold")
public class GoldPriceController {

    @Autowired
    private GoldPriceService goldPriceService;

    @GetMapping("/current")
    public String getCurrentGoldPrice() {
        return goldPriceService.getGoldPrice();
    }
}
