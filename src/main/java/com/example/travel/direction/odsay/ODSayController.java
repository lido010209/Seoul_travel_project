package com.example.travel.direction.odsay;

import com.example.travel.direction.odsay.template.ODSayTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("odsay")
public class ODSayController {
    private final ODSayTemplate odSayTemplate;
    @GetMapping("directions")
    public Map<String, Object> directions(
            @RequestParam("sx") String sx,
            @RequestParam("sy") String sy,
            @RequestParam("ex") String ex,
            @RequestParam("ey") String ey
    ){
        return odSayTemplate.directions(sx, sy, ex, ey);
    }

    @GetMapping("loadLane")
    public Map<String, Object> loadLane(@RequestParam("mapObj") String mapObj){
        return odSayTemplate.loadLane(mapObj);
    }

    @GetMapping("stops")
    public Map<String, Object> stops(
            @RequestParam("mapx") String mapx,
            @RequestParam("mapy") String mapy,
            @RequestParam("lang") String lang
    ){
        return odSayTemplate.stops(mapx, mapy, lang);
    }
}
