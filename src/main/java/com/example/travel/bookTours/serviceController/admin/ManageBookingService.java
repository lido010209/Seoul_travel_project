package com.example.travel.bookTours.serviceController.admin;

import com.example.travel.bookTours.dto.BookToursDto;
import com.example.travel.bookTours.entity.BookTours;
import com.example.travel.bookTours.repo.BookToursRepo;
import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.repo.PackageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageBookingService {
    private final BookToursRepo bookToursRepo;
    private final PackageRepo packageRepo;
    public BookToursDto confirm(Long bookId){
        BookTours booking = bookToursRepo.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("No exist booking"));
        booking.setStatus(BookTours.Status.Confirmed);
        return BookToursDto.dto(bookToursRepo.save(booking));
    }

    public List<BookToursDto> allBooking(){
        List<BookToursDto> all = new ArrayList<>();
        for (BookTours booking: bookToursRepo.findAll()){
            all.add(BookToursDto.dto(booking));
        }
        return all;
    }

    public void delete(Long bookId){
        BookTours booking = bookToursRepo.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("No exist booking"));
        if (!booking.getStatus().equals(BookTours.Status.Cancel_booking))
            throw new IllegalArgumentException("Cannot delete this booking!!!");
        bookToursRepo.delete(booking);
    }

    // Search list booking
    public List<BookToursDto> bookingsByPackage(Long packageId){
        List<BookToursDto> result= new ArrayList<>();
        Package pack = packageRepo.findById(packageId).orElseThrow(
                    ()-> new IllegalArgumentException("No exist package!!!")
            );
        for (BookTours booking: bookToursRepo.bookingsByPackage(pack)){
            result.add(BookToursDto.dto(booking));
        }
        return result;
    }
    public List<BookToursDto> bookingsByStatus(BookTours.Status status){
        List<BookToursDto> result= new ArrayList<>();
        for (BookTours booking: bookToursRepo.findByStatus(status)){
            result.add(BookToursDto.dto(booking));
        }
        return result;
    }
}
