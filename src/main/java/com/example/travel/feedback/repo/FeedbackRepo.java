package com.example.travel.feedback.repo;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.feedback.entity.Feedback;
import com.example.travel.tourPackage.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByCustomerAndTourPackage(UserEntity user, Package tourPackage);
    List<Feedback> findByTourPackage(Package tourPackage);
}
