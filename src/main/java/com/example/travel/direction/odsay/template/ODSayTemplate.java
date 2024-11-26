package com.example.travel.direction.odsay.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class ODSayTemplate {
    @Value("${odsay.apiKey}")
    private String odsayKey;

    private String uriString(String endpoint) throws UnsupportedEncodingException {
        String url = UriComponentsBuilder.fromUriString("https://api.odsay.com/v1/api").path("/"+endpoint)
                .queryParam("apiKey", URLEncoder.encode(odsayKey, "UTF-8"))
                .encode().build(true).toUriString();
        return url;
    }

    // Find nearest bus stops, subway station
    public Map<String, Object> stops(String mapx, String mapy, String language) {
        Map<String, Object> allStops = new HashMap<>();
        try {
            String uriString = uriString("pointBusStation");
            URI uri = UriComponentsBuilder.fromUriString(uriString)
                    .queryParam("lang", language)
                    .queryParam("radius", "300")
                    .queryParam("x", mapx)
                    .queryParam("y", mapy)
                    .encode().build(true).toUri();

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(uri, String.class);
            allStops = new ObjectMapper().readValue(response.toString(), Map.class);
        }catch (JsonProcessingException | UnsupportedEncodingException e){
            e.getMessage();
        }

        return (Map<String, Object>) allStops.get("result");
    }

    public Map<String, Object> directions(
            String sx, String sy,
            String ex, String ey
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            String uriString = uriString("searchPubTransPathT");
            URI uri = UriComponentsBuilder.fromUriString(uriString)
                    .queryParam("SX", sx)
                    .queryParam("SY", sy)
                    .queryParam("EX", ex)
                    .queryParam("EY", ey)
                    .encode().build(true).toUri();

//        RequestEntity<Void> request = RequestEntity.get(uri)
//                .header("Content-Type", "application/json;charset=UTF-8")
//                .build();
            RestTemplate restTemplate= new RestTemplate();

            String response = restTemplate.getForObject(uri, String.class);
            map = new ObjectMapper().readValue(response.toString(), Map.class);
        } catch (UnsupportedEncodingException | JsonProcessingException e){
            e.getMessage();
        }
        return (Map<String, Object>) map.get("result");
    }


    public Map<String, Object> loadLane(String mapObj) {
        Map<String, Object> map = new HashMap<>();
        try {
            String uriString = uriString("loadLane");
            URI uri = UriComponentsBuilder.fromUriString(uriString)
                    .queryParam("mapObject", "0:0@"+mapObj)
                    .encode().build(true).toUri();
            String response = new RestTemplate().getForObject(uri, String.class);
            map = new ObjectMapper().readValue(response.toString(), Map.class);
        } catch (UnsupportedEncodingException | JsonProcessingException e){
            e.getMessage();
        }
        return (Map<String, Object>) map.get("result");
    }
}
