package com.example.travel.direction.naverSearch.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NSearchLocationTemplate {
    @Value("${naver.client-id}")
    private String NAVER_API_ID;

    @Value("${naver.secret}")
    private String NAVER_API_SECRET;

    public List<Map<String, String>> resultSearch(String keyword){
        List<Map<String, String>> listResults = new ArrayList<>();

        try {
            // UTF-8로 인코딩된 검색어 생성
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(keyword);
            String encode = StandardCharsets.UTF_8.decode(buffer).toString();

            // 네이버 검색 API를 호출하기 위한 URI 생성
            URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com").path("/v1/search/local")
                    .queryParam("query", encode).queryParam("display", 5).queryParam("start", 1)
                    .queryParam("sort", "random").encode().build().toUri();

            // RestTemplate을 사용하여 네이버 API에 요청을 보냄
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> req = RequestEntity.get(uri).header("X-Naver-Client-Id", NAVER_API_ID)
                    .header("X-Naver-Client-Secret", NAVER_API_SECRET).build();

            // API 응답 데이터를 JSON 형식으로 변환
            ResponseEntity<String> response = restTemplate.exchange(req, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> path = objectMapper.readValue(response.getBody().toString(), Map.class);
            listResults = (List<Map<String, String>>) path.get("items");
            for (Map<String, String> item: listResults){
                double latitude = Double.parseDouble(item.get("mapy").toString()) / 1e7; // 위도
                double longitude = Double.parseDouble(item.get("mapx").toString()) / 1e7; // 경도
                item.put("mapy", String.valueOf(latitude));
                item.put("mapx", String.valueOf(longitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listResults;
    }
}
