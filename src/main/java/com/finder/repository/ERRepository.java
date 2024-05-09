package com.finder.repository;

import com.finder.domain.ER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ERRepository extends JpaRepository<ER, String> {
    List<ER> findByLatitudeBetweenAndLongitudeBetween(Double swLat, Double neLat, Double swLon, Double neLon);
}
