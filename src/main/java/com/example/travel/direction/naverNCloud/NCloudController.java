package com.example.travel.direction.naverNCloud;

import com.example.travel.direction.naverNCloud.template.NCloudTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("n-cloud")
public class NCloudController {
    private final NCloudTemplate cloudTemplate;
    @GetMapping
    public Map<String, Object> route(
            @RequestParam("sx") String sx,
            @RequestParam("sy") String sy,
            @RequestParam("ex") String ex,
            @RequestParam("ey") String ey,
            @RequestParam("option") String option
            // 도로: traoptimal, 자동차: traavoidcaronly
    ){
        return cloudTemplate.route(sx, sy, ex, ey, option);
    }
}
