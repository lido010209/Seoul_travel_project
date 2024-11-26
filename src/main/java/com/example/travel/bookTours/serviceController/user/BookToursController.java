package com.example.travel.bookTours.serviceController.user;

import com.example.travel.bookTours.dto.BookToursDto;
import com.example.travel.bookTours.entity.BookTours;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
public class BookToursController {
    private final BookToursService bookToursService;
    @PostMapping
    public BookToursDto booking(
            @RequestBody
            BookToursDto dto
    ){
        return bookToursService.booking(dto);
    }

    @PutMapping("{bookId}")
    public BookToursDto payment(
            @PathVariable("bookId")
            Long bookId
    ){
        return bookToursService.paying(bookId);
    }
    @PutMapping("{bookId}/rating")
    public BookToursDto rating(
            @PathVariable("bookId")
            Long bookId,
            @RequestBody
            BookToursDto dto
    ){
        return bookToursService.rating(bookId, dto);
    }

    @GetMapping("{bookId}")
    public BookToursDto checkBooking(@PathVariable("bookId") Long bookId){
        return bookToursService.checkBooking(bookId);
    }

    @GetMapping
    public List<BookToursDto> listBooking(){
        return bookToursService.listBooking();
    }

    @PutMapping("{bookId}/cancel")
    public BookToursDto cancel(@PathVariable("bookId")
                                   Long bookId){
        return bookToursService.cancel(bookId);
    }
    @GetMapping("search")
    public List<BookToursDto> result(
            @RequestParam(value = "packageId", required = false) Long packageId,
            @RequestParam(value = "status", required = false) BookTours.Status status
    ){
        if (packageId!=null) return bookToursService.userBookingsByPackage(packageId);
        return bookToursService.userBookingsByStatus(status);
    }
}
