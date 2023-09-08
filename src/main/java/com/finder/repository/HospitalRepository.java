package com.finder.repository;

import com.finder.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query("select h from Hospital h " +
            "where h.latitude >= :southWestLat and h.longitude >= :southWestLon " +
            "and h.latitude <= :northEastLat and h.longitude <= :northEastLon")
    List<Hospital> findHospitalMap(Double southWestLat, Double southWestLon, Double northEastLat, Double northEastLon);
}