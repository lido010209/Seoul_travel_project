package com.example.travel.bookTours.dto;

import com.example.travel.bookTours.entity.BookTours;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookToursDto {
    private Long id;
    private LocalDate departureDay;
    private Integer numOfPeople;
    private Integer payment;
    private BookTours.Status status;
    private Long packageId;
    private String customerName;
    private String customerPhone;
    private Integer coupon;
    private String packageTitle;
    private Integer rating;
    private Long customerId;

    public static BookToursDto dto (BookTours entity){
        BookToursDto bookToursDto = BookToursDto.builder()
                .id(entity.getId())
                .numOfPeople(entity.getNumOfPeople())
                .status(entity.getStatus())
                .payment(entity.getPayment())
                .packageId(entity.getTourPackage().getId())
                .departureDay(entity.getDepartureDay())
                .customerName(entity.getCustomer().getName())
                .coupon(entity.getCoupon().getValue())
                .packageTitle(entity.getTourPackage().getTitle())
                .customerPhone(entity.getCustomer().getPhone())
                .rating(entity.getRating())
                .customerId(entity.getCustomer().getId())
                .build();
        return bookToursDto;
    }
}
