package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class GoldPriceService {

    @Value("${goldapi.url}")
    private String apiUrl;

    @Value("${goldapi.key}")
    private String apiKey;

    @Value("${goldapi.file.path}")
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public String getGoldPrice(LocalDate date) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-access-token", apiKey);

        String urlWithDate=apiUrl+"/" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(urlWithDate, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
    public void writeGoldPriceToFile(LocalDate date,LocalDate endDate) {
        while(!date.isAfter(endDate)) {
        String price=getGoldPrice(date);
        String data=price + System.lineSeparator();
        try {
            FileWriter fileWriter = new FileWriter(filePath,true);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        date=date.plusDays(1);
        }
    }
}
