package com.example.travel.direction.naverNCloud.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class NCloudTemplate {
    @Value("${naver-cloud.client-id}")
    private String clientId;
    @Value("${naver-cloud.secret}")
    private String secret;

    public Map<String, Object> route(String sx, String sy, String ex, String ey, String option) {
        List<Map<String, Object>> path = new ArrayList<>();
        try {
            URI uri = UriComponentsBuilder. fromUriString("https://naveropenapi.apigw.ntruss.com/map-direction/v1").path("/driving")
                    .queryParam("option", option)
                    .queryParam("start", sx + "," + sy)
                    .queryParam("goal", ex + ","+ey)
                    .encode().build().toUri();

            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> req = RequestEntity.get(uri).header("X-NCP-APIGW-API-KEY-ID", clientId)
                    .header("X-NCP-APIGW-API-KEY", secret).build();

            ResponseEntity<String> response = restTemplate.exchange(req, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(response.getBody().toString(), Map.class);
            Map<String, Object> route = (Map<String, Object>) map.get("route");
            path = (List<Map<String, Object>>) route.get(option);
        } catch ( JsonProcessingException e){
            e.getMessage();
        }
        return path.get(0);
    }
}
