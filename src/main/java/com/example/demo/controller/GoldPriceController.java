package com.example.demo.controller;

import com.example.demo.service.GoldPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/gold")
public class GoldPriceController {

    @Autowired
    private GoldPriceService goldPriceService;

    @GetMapping("/save")
    public String getCurrentGoldPrice(@RequestParam String startDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.now().minusDays(1);
        goldPriceService.writeGoldPriceToFile(start,end);
        return goldPriceService.getFilePath();
    }
}
