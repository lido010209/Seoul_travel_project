package com.example.travel.tourPackage.service;

import com.example.travel.bookTours.entity.BookTours;
import com.example.travel.bookTours.repo.BookToursRepo;
import com.example.travel.tourPackage.dto.PackageDto;
import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.repo.PackageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final PackageRepo packageRepo;
    private final BookToursRepo bookToursRepo;

    public PackageDto readOne(Long packageId){
        Package tourPackage = packageRepo.findById(packageId)
                .orElseThrow(()-> new IllegalArgumentException("No exist tour package"));
        Double avg = 0.0;
        List<BookTours> list = bookToursRepo.bookingsByPackage(tourPackage);
        Integer num = 0;
        for (BookTours booking: list){
            if (booking.getRating()!=null){
                avg+= booking.getRating();
                num++;
            }
        }
        if (num!=0) tourPackage.setAvgRating(avg/num);
        tourPackage.setNumOfRating(num);
//        bookToursRepo.bookingsByPackage(tourPackage).stream().map(BookTours::getRating).toList();
        return PackageDto.dto(packageRepo.save(tourPackage));
    }

    public List<PackageDto> readAll(){
        List<PackageDto> allPackages = new ArrayList<>();
        for(Package tour: packageRepo.findAll()){
            allPackages.add(PackageDto.dto(tour));
        }
        return allPackages;
    }
}
