package com.example.travel.tourist.template.eng;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tours/eng")
public class EngController {
    private final EngTemplate engTemplate;

    @GetMapping("landmark")
    public List<Map<String, Object>> allLandmarkPerPage(
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("sigunguCode") String sigunguCode,
            @RequestParam("keyword") String keyword
    ){
        if (keyword!=null && sigunguCode!=null)
            return engTemplate.search(pageNo, 76, sigunguCode, keyword);
        else if (keyword!=null)
            return engTemplate.search(pageNo, 76, null, keyword);
        else if (sigunguCode!=null)
            return engTemplate.allInfoPerPage(pageNo, 76, sigunguCode);
        return engTemplate.allInfoPerPage(pageNo, 76, null);
    }

    @GetMapping("restaurant")
    public List<Map<String, Object>> allRestaurantPerPage(@RequestParam("pageNo") Integer pageNo){
        return engTemplate.allInfoPerPage(pageNo, 82, null);
    }
    @GetMapping("accommodation")
    public List<Map<String, Object>> allAccommodationPerPage(@RequestParam("pageNo") Integer pageNo){
        return engTemplate.allInfoPerPage(pageNo, 80, null);
    }

    @GetMapping("detail/{contentId}")
    public Map<String, Object> oneTour(@PathVariable("contentId") String contentId){
        return engTemplate.detailInfo(contentId);
    }
}
