package uz.architect.springbootadvanced.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.architect.springbootadvanced.pojo.ApiResponse;

import java.net.URI;
import java.util.Objects;

@Service
public class ShortenUrlServiceImpl implements ShortenUrlService {

    private final String apiKey = "0498e38314mshdd9d8958ad9bb70p18cd90jsnd32ea9784687";
    private final String URL = "https://url-shortener-service.p.rapidapi.com/shorten";
    private final RestTemplate restTemplate;

    @Autowired
    public ShortenUrlServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String shortenUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/x-www-form-urlencoded");
        headers.add("X-RapidAPI-Key", apiKey);
        headers.add("X-RapidAPI-Host", "url-shortener-service.p.rapidapi.com");
        HttpEntity<String> httpEntity = new HttpEntity<>("url=" + url, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(URI.create(URL), HttpMethod.POST, httpEntity, ApiResponse.class);
        return Objects.requireNonNull(response.getBody()).getUrl();
    }
}
