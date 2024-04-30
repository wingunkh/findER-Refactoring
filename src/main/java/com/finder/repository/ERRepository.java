package com.finder.repository;

import com.finder.domain.ER;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ERRepository extends JpaRepository<ER, String> {
    List<ER> findByLatitudeBetweenAndLongitudeBetween(Double southWestLat, Double northEastLat, Double southWestLon, Double northEastLon);
}
