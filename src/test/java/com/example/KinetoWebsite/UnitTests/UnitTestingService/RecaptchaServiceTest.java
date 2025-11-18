package com.example.KinetoWebsite.UnitTests.UnitTestingService;

import com.example.KinetoWebsite.Service.RecaptchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecaptchaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecaptchaService recaptchaService;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(recaptchaService, "recaptchaSecretKey", "dummy-secret-key");
    }

    @Test
    void verifyRecaptcha_WhenTokenIsValid_ShouldReturnTrue(){

        //Arrange
        String validToken = "valid-token";
        Map<String, Object> googleResponse = new HashMap<>();
        googleResponse.put("success", true);

        when(restTemplate.postForObject(
                any(String.class),
                nullable(Object.class),
                any(Class.class),
                any(Map.class)
        )).thenReturn(googleResponse);

        //Act
        boolean result = recaptchaService.verifyRecaptcha(validToken);

        //assert
        assertTrue(result);
    }
}
