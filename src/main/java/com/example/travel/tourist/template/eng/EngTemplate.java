package com.example.travel.tourist.template.eng;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EngTemplate {
    @Value("${tour-api.serviceKey}")
    private String serviceKey;

    private String root(String endpoint){
        String url = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/B551011/EngService1").path("/"+endpoint)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileApp", "AppTest")
                .queryParam("MobileOS", "ETC")
                .queryParam("_type", "json")
                .encode().build(true).toUriString();
        return url;
    }

    private Map<String, Object> itemsMap(String url){
        Map<String, Object> def = new HashMap<>();
        try {
            URI uri = new URI(url);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(
                    uri,
                    String.class
            );
            Map<String, Object> map = new ObjectMapper().readValue(response.toString(), Map.class);
            Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
            Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
            def = (Map<String, Object>) bodyMap.get("items");
        } catch (URISyntaxException | JsonProcessingException e){
            e.getMessage();
        }
        return def;
    }

    // All tourists locate in Seoul
    public List<Map<String, Object>> allInfoPerPage(Integer pageNo, Integer contentTypeId, String sigunguCode){
        List<Map<String, Object>> all = new ArrayList<>();
        String url = UriComponentsBuilder.fromUriString(root("areaBasedList1"))
                .queryParam("areaCode", 1)
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", pageNo)
                .queryParam("sigunguCode",sigunguCode)
                .encode().build(true).toUriString();
        return (List<Map<String, Object>>) itemsMap(url).get("item");
    }

    // Get information of only one tour based on contentId
    public Map<String, Object> detailInfo (String contentId){
        Map<String, Object> item = new HashMap<>();
        String url = UriComponentsBuilder.fromUriString(root("detailCommon1"))
                .queryParam("contentId", contentId)
                .queryParam("overviewYN", "Y")
                .queryParam("mapinfoYN","Y")
                .queryParam("addrinfoYN", "Y")
                .queryParam("defaultYN", "Y")
                .encode().build(true).toUriString();
        return ((List<Map<String, Object>>) itemsMap(url).get("item")).get(0);
    }

    public List<Map<String, Object>> search(Integer pageNo, Integer contentTypeId, String sigunguCode, String keyword){
        List<Map<String, Object>> all = new ArrayList<>();
        String url = UriComponentsBuilder.fromUriString(root("searchKeyword1"))
                .queryParam("areaCode", 1)
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", pageNo)
                .queryParam("sigunguCode",sigunguCode)
                .queryParam("keyword", keyword)
                .encode().build(true).toUriString();
        return (List<Map<String, Object>>) itemsMap(url).get("item");
    }
}
