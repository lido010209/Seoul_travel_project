package com.example.travel.tourPackage.createPackage;

import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.entity.Schedule;
import com.example.travel.tourPackage.repo.PackageRepo;
import com.example.travel.tourPackage.repo.ScheduleRepo;
import org.springframework.stereotype.Component;

@Component
public class PackageInfo {
    private final ScheduleRepo scheduleRepo;
    private final PackageRepo packageRepo;

    public PackageInfo(ScheduleRepo scheduleRepo, PackageRepo packageRepo) {
        this.scheduleRepo = scheduleRepo;
        this.packageRepo = packageRepo;
//        this.package1();
//        this.package2();
//        this.package3();
    }



}
