package com.finder.repository;

import com.finder.domain.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepository extends JpaRepository<Bed, String> {
    Bed findByHpID(String hpID);
}
