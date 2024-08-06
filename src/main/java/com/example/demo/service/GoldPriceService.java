package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class GoldPriceService {

    @Value("${goldapi.url}")
    private String apiUrl;

    @Value("${goldapi.file.path}")
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public String getGoldPrice(LocalDate date1, LocalDate date2) {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithDate = apiUrl + "/" + date1.format(DateTimeFormatter.ISO_DATE) + "/"+date2.format(DateTimeFormatter.ISO_DATE)+"/";

        try {
            ResponseEntity<String> response = restTemplate.exchange(urlWithDate, HttpMethod.GET, HttpEntity.EMPTY, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                System.out.println("No data found for date: " + date1+" and date: " + date2);
                return null;
            } else {
                throw e;
            }
        }
    }

    public void writeGoldPriceToFile(LocalDate startDate, LocalDate endDate) {
            String price = getGoldPrice(startDate, endDate);
            if (price != null) {
                String data = price + System.lineSeparator();
                try (FileWriter fileWriter = new FileWriter(filePath, true)) {
                    fileWriter.write(data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
