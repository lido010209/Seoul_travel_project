package com.example.travel.direction.naverSearch;

import com.example.travel.direction.naverSearch.template.NSearchLocationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("n-search-location")
public class NSearchLocationController {
    private final NSearchLocationTemplate positionTemplate;
    @GetMapping
    public List<Map<String, String>> result(@RequestParam("keyword") String keyword){
        return positionTemplate.resultSearch(keyword);
    }
}
