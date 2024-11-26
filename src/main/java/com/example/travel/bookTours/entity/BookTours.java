package com.example.travel.bookTours.entity;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.baseEntity.BaseEntity;
import com.example.travel.tourPackage.entity.Package;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book_tours")
public class BookTours extends BaseEntity {
    private LocalDate departureDay;
    private Integer numOfPeople;
    private Integer payment;
    private Integer rating;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Coupon coupon;
    @ManyToOne
    private Package tourPackage;
    @ManyToOne
    private UserEntity customer;
    public enum Status {
        Booking_successful, Cancel_booking, Payment_successful, Confirmed
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum Coupon {
        ZERO(0),
        TWENTY(20),
        THIRTY(30),
        FORTY(40),
        FIFTY(50),
        SIXTY(60),
        SEVENTY(70),
        EIGHTY(80),
        HUNDRED(100);

        private Integer value;
    }
}
