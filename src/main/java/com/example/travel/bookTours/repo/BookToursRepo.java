package com.example.travel.bookTours.repo;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.bookTours.entity.BookTours;
import com.example.travel.tourPackage.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookToursRepo extends JpaRepository<BookTours, Long> {
//    List<BookTours> findByUserEntity(UserEntity customer);
    @Query("SELECT DISTINCT b FROM BookTours b JOIN FETCH b.customer WHERE b.customer= :user")
    List<BookTours> listBooking(@Param("user") UserEntity user);
    //Search by admin
    @Query("SELECT DISTINCT b FROM BookTours b JOIN FETCH b.tourPackage WHERE b.tourPackage=:tour")
    List<BookTours> bookingsByPackage(@Param("tour")Package tourPackage);
    List<BookTours> findByStatus (BookTours.Status status);

    // Search by user
    @Query("SELECT DISTINCT b FROM BookTours b JOIN FETCH b.customer JOIN FETCH b.tourPackage WHERE b.customer= :user AND b.tourPackage = :tour")
    List<BookTours> userBookingByPackage(@Param("user") UserEntity user, @Param("tour") Package tourPackage);
    @Query("SELECT DISTINCT b FROM BookTours b JOIN FETCH b.customer WHERE b.customer= :user AND b.status = :status")
    List<BookTours> userBookingByStatus(@Param("user") UserEntity user, @Param("status") BookTours.Status status);

//    // Booking with rating
//    @Query("SELECT DISTINCT b FROM BookTours b JOIN FETCH b.tourPackage WHERE b.tourPackage=:tour")
//    List<BookTours> bookingsByPackageWithRating(@Param("tour")Package tourPackage);
}
