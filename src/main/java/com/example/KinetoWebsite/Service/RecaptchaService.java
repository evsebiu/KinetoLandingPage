package com.example.KinetoWebsite.Service;


import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret.key}")
        private String recaptchaSecretKey;

    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyRecaptcha(String recaptchaResponse){

        if (recaptchaResponse == null || recaptchaResponse.isEmpty()){
            return false;
        }
        try{

            Map<String, String> requestParams = new HashMap<>();
            requestParams.put("secret", recaptchaSecretKey);
            requestParams.put("response", recaptchaResponse);

            Map<String, Object> response = this.restTemplate.postForObject(
                    RECAPTCHA_VERIFY_URL + "?secret={secret}&response={response}",
                    null,
                    Map.class,
                    requestParams
            );

            if (response != null && response.containsKey("success")){
                Object successsObj = response.get("success");
                if (successsObj instanceof Boolean){
                    return (Boolean) successsObj;
                }
            }
        } catch (Exception e){
            System.err.println("reCaptcha verification failed " + e.getMessage());
        }
        return false;
    }
}
