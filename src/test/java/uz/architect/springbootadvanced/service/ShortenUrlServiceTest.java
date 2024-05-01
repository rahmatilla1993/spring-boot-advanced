package uz.architect.springbootadvanced.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest
class ShortenUrlServiceTest {

    private ShortenUrlService shortenUrlService;
    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        shortenUrlService = new ShortenUrlServiceImpl(restTemplate);
    }

    @Test
    void shortenUrl() {
        String shortenUrl = shortenUrlService.shortenUrl("https://github.com/jlkesh/pdp_online_lessons_spring_module_3/blob/call_api_resttemplate/src/main/java/uz/pdp/springadvanced/resource/CommentResource.java");
        assertEquals("https://goolnk.com/540MdG", shortenUrl);
    }
}