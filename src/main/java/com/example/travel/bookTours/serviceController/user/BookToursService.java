package com.example.travel.bookTours.serviceController.user;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.bookTours.dto.BookToursDto;
import com.example.travel.bookTours.entity.BookTours;
import com.example.travel.bookTours.repo.BookToursRepo;
import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.repo.PackageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookToursService {
    private final BookToursRepo bookToursRepo;
    private final PackageRepo packageRepo;
    private final UserComponent userComponent;
    //Create
    @Transactional
    public BookToursDto booking(BookToursDto dto){
        Package pack = packageRepo.findById(dto.getPackageId())
                .orElseThrow(()-> new IllegalArgumentException("No exist package!!!"));
        Integer payment = pack.getPrice()* dto.getNumOfPeople();
        BookTours.Coupon coupon = BookTours.Coupon.ZERO;
        if (dto.getNumOfPeople()<5) coupon = BookTours.Coupon.ZERO;
        else if (dto.getNumOfPeople()<10) coupon= BookTours.Coupon.TWENTY;
        else if (dto.getNumOfPeople()<20) coupon= BookTours.Coupon.THIRTY;
        else coupon= BookTours.Coupon.FIFTY;
        payment = payment* (100-coupon.getValue())/100;
        BookTours bookTours = BookTours.builder()
                .tourPackage(pack)
                .numOfPeople(dto.getNumOfPeople())
                .customer(userComponent.userLogin())
                .departureDay(dto.getDepartureDay())
                .status(BookTours.Status.Booking_successful)
                .coupon(coupon)
                .payment(payment)
                .build();
        return BookToursDto.dto(bookToursRepo.save(bookTours));
    }

    public BookToursDto coupon(BookToursDto dto, Long bookId){
        BookTours tour = bookToursRepo.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("No exist booking"));
        if (!tour.getCustomer().getId().equals(userComponent.userLogin().getId()))
            throw new IllegalArgumentException("This booking is not available!!!");
        Integer payment = tour.getPayment()*dto.getCoupon()/100;
        tour.setPayment(payment);
        return BookToursDto.dto(bookToursRepo.save(tour));
    }
    //Update payment
    public BookToursDto paying(Long bookId){
        BookTours tour = bookToursRepo.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("No exist booking"));
        if (!tour.getCustomer().getId().equals(userComponent.userLogin().getId()))
            throw new IllegalArgumentException("This booking is not available!!!");
        tour.setStatus(BookTours.Status.Payment_successful);
        return BookToursDto.dto(bookToursRepo.save(tour));
    }

    //Read one
    public BookToursDto checkBooking(Long bookId){
        BookTours tour = bookToursRepo.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("No exist booking"));
        if (!tour.getCustomer().getId().equals(userComponent.userLogin().getId()))
            throw new IllegalArgumentException("This booking is not available!!!");
        return BookToursDto.dto(tour);
    }

    //Read all
    public List<BookToursDto> listBooking(){
        List<BookToursDto> list = new ArrayList<>();
        List<BookTours> toursList = bookToursRepo.listBooking(userComponent.userLogin());
        for (BookTours booking: toursList){
            list.add(BookToursDto.dto(booking));
        }
        return list;
    }

    public BookToursDto cancel(Long bookId){
        BookTours tour = bookToursRepo.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("No exist booking"));
        if (!tour.getCustomer().getId().equals(userComponent.userLogin().getId()))
            throw new IllegalArgumentException("This booking is not available!!!");
        tour.setStatus(BookTours.Status.Cancel_booking);
        return BookToursDto.dto(bookToursRepo.save(tour));
    }

    public BookToursDto rating(Long bookId, BookToursDto dto){
        BookTours tour = bookToursRepo.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("No exist booking"));
        if (!tour.getCustomer().getId().equals(userComponent.userLogin().getId()))
            throw new IllegalArgumentException("This booking is not available!!!");
        tour.setRating(dto.getRating());
        return BookToursDto.dto(bookToursRepo.save(tour));
    }

    // Search list booking
    public List<BookToursDto> userBookingsByPackage(Long packageId){
        List<BookToursDto> result= new ArrayList<>();
        Package pack = packageRepo.findById(packageId).orElseThrow(
                ()-> new IllegalArgumentException("No exist package!!!")
        );
        UserEntity user = userComponent.userLogin();
        for (BookTours booking: bookToursRepo.userBookingByPackage(user, pack)){
            result.add(BookToursDto.dto(booking));
        }
        return result;
    }

    public List<BookToursDto> userBookingsByStatus(BookTours.Status status){
        List<BookToursDto> result= new ArrayList<>();
        for (BookTours booking: bookToursRepo.userBookingByStatus(userComponent.userLogin(), status)){
            result.add(BookToursDto.dto(booking));
        }
        return result;
    }
}
