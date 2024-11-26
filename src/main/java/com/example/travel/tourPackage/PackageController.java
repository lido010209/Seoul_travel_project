package com.example.travel.tourPackage;

import com.example.travel.tourPackage.service.PackageService;
import com.example.travel.tourPackage.dto.PackageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("packages")
@RequiredArgsConstructor
public class PackageController {
    private final PackageService packageService;
    @GetMapping
    public List<PackageDto> allPackages(){
        return packageService.readAll();
    }
    @GetMapping("{packageId}")
    public PackageDto onePackage(@PathVariable("packageId") Long packageId){
        return packageService.readOne(packageId);
    }
}
