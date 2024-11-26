package com.example.travel.tourPackage.repo;

import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    @Query("SELECT DISTINCT s FROM Schedule s JOIN FETCH s.tourPackage WHERE s.tourPackage = :tour")
    List<Schedule> schedules(@Param("tour") Package tourPackage);
}
