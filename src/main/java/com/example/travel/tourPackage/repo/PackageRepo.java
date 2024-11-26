package com.example.travel.tourPackage.repo;

import com.example.travel.tourPackage.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackageRepo extends JpaRepository<Package, Long> {
//    @Query("SELECT DISTINCT p FROM Package p JOIN FETCH")
}
